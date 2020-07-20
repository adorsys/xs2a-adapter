package de.adorsys.xs2a.adapter.codegen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.squareup.javapoet.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.squareup.javapoet.TypeSpec.Kind.ENUM;

public class CodeGenerator {
    public static final String TOOLNAME = "xs2a-adapter-codegen";

    private final OpenAPI api;
    private final Consumer<JavaFile> action;
    private final String apiPackage;
    private final String modelPackage;
    private String schemaName;
    private String propertyName;
    private List<TypeSpec> nestedTypes = new ArrayList<>();
    private Map<String, List<MethodSpec>> interfaceToMethodSpecs;
    private Map<String, TypeSpec> models;

    public CodeGenerator(OpenAPI api, Consumer<JavaFile> action, String basePackage) {
        this.api = api;
        this.action = action;
        apiPackage = basePackage + ".rest.api";
        modelPackage = basePackage + ".api.model";
    }

    public void generate(Map<String, String> operationToInterface) {
        generateModels();
        generateInterfaces(operationToInterface);
    }

    private void generateInterfaces(Map<String, String> operationToInterface) {
        interfaceToMethodSpecs = new HashMap<>();
        api.getPaths().forEach((path, pathItem) -> {
            pathItem.readOperationsMap().forEach((httpMethod, operation) -> {
                String operationId = operation.getOperationId();
                TypeName returnType = returnType(operation);
                RequestBody requestBody = operation.getRequestBody();
                if (requestBody == null) {
                    addMethod(operationToInterface.get(operationId), operationId, path, httpMethod, operation, returnType, null);
                } else {
                    for (String mediaType : getMediaTypes(requestBody)) {
                        addMethod(operationToInterface.get(operationId), operationId, path, httpMethod, operation, returnType, mediaType);
                    }
                }
            });
        });

        interfaceToMethodSpecs.forEach((interfaceName, methodSpecs) -> {
            JavaFile javaFile = JavaFile.builder(apiPackage, TypeSpec.interfaceBuilder(interfaceName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(generatedAnnotation())
                .addMethods(methodSpecs)
                .build())
                .build();

            action.accept(javaFile);
        });
    }

    private void addMethod(String interfaceName,
                           String operationId, String path,
                           PathItem.HttpMethod httpMethod,
                           Operation operation, TypeName returnType, String mediaType) {

        List<ParameterSpec> parameterSpecs = parameterSpecs(operation, mediaType);

        AnnotationSpec.Builder requestMappingBuilder = AnnotationSpec.builder(RequestMapping.class)
            .addMember("value", "$S", path)
            .addMember("method", "$T.$L", RequestMethod.class, httpMethod.name());
        if (mediaType != null) {
            requestMappingBuilder.addMember("consumes", "$S", mediaType);
        }
        AnnotationSpec requestMapping = requestMappingBuilder.build();

        MethodSpec methodSpec = MethodSpec.methodBuilder(operationId)
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addAnnotation(requestMapping)
            .addParameters(parameterSpecs)
            .returns(returnType)
            .build();
        interfaceToMethodSpecs.merge(interfaceName, new ArrayList<>(List.of(methodSpec)),
            (list, method) -> {
                list.addAll(method);
                return list;
            });
    }

    private TypeName returnType(Operation operation) {
        for (Map.Entry<String, ApiResponse> entry : operation.getResponses().entrySet()) {
            String httpCode = entry.getKey();
            if (httpCode.startsWith("2")) {
                ApiResponse apiResponse = entry.getValue();
                // remember the name for inline (anonymous) schema definitions, such as OK_200_TransactionDetails
                String refName = null;
                if (apiResponse.get$ref() != null) {
                    refName = getSimpleName(apiResponse.get$ref());
                    apiResponse = api.getComponents().getResponses().get(refName);
                }
                Content content = apiResponse.getContent();

                ClassName jsonComposedSchemaType = ClassName.get(Object.class);
                TypeName returnTypeName = getTypeName(content, jsonComposedSchemaType, refName);
                return ParameterizedTypeName.get(ClassName.get(ResponseEntity.class), returnTypeName);
            }
        }
        throw new RuntimeException("2xx Success response not found");
    }

    private Set<String> getMediaTypes(RequestBody requestBody) {
        if (requestBody.getContent() == null && requestBody.get$ref() != null) {
            requestBody = api.getComponents().getRequestBodies().get(getSimpleName(requestBody.get$ref()));
        }
        return new LinkedHashSet<>(requestBody.getContent().keySet());
    }

    private List<ParameterSpec> parameterSpecs(Operation operation, String mediaType) {
        List<ParameterSpec> parameterSpecs = new ArrayList<>();
        for (Parameter parameter : operation.getParameters()) {
            if (parameter.get$ref() != null) {
                parameter = api.getComponents().getParameters().get(getSimpleName(parameter.get$ref()));
            }
            // custom param, added manually before headers
            if ("additionalQueryParameters".equals(parameter.getName())) {
                continue;
            }
            Schema parameterSchema = parameter.getSchema();
            if (parameterSchema.get$ref() != null) {
                parameterSchema = api.getComponents().getSchemas().get(getSimpleName(parameterSchema.get$ref()));
            }
            switch (parameter.getIn()) {
                case "path":
                    ParameterSpec pathParameter = ParameterSpec.builder(toJavaTypeName(parameterSchema, parameter.getName()), toCamelCase(parameter.getName()))
                        .addAnnotation(AnnotationSpec.builder(PathVariable.class)
                            .addMember("value", "$S", parameter.getName())
                            .build())
                        .build();
                    parameterSpecs.add(pathParameter);
                    break;
                case "query":
                    ParameterSpec queryParameter = ParameterSpec.builder(
                        toJavaTypeName(parameterSchema, parameter.getName()), toCamelCase(parameter.getName()))
                        .addAnnotation(AnnotationSpec.builder(RequestParam.class)
                            .addMember("value", "$S", parameter.getName())
                            .addMember("required", "$L", parameter.getRequired())
                            .build())
                        .build();
                    parameterSpecs.add(queryParameter);
                    break;
                case "header":
                    // ignore
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        ParameterSpec paramsParameter = ParameterSpec.builder(
            ParameterizedTypeName.get(Map.class, String.class, String.class), "parameters")
            .addAnnotation(RequestParam.class)
            .build();
        parameterSpecs.add(paramsParameter);
        ParameterSpec headersParameter = ParameterSpec.builder(ParameterizedTypeName.get(Map.class, String.class, String.class), "headers")
            .addAnnotation(RequestHeader.class)
            .build();
        parameterSpecs.add(headersParameter);

        RequestBody requestBody = operation.getRequestBody();
        if (requestBody != null) {
            if (requestBody.get$ref() != null) {
                requestBody = api.getComponents().getRequestBodies().get(getSimpleName(requestBody.get$ref()));
            }
            Map<String, MediaType> content = Collections.singletonMap(mediaType, requestBody.getContent().get(mediaType));
            ClassName jsonComposedSchemaType = ClassName.get(ObjectNode.class);
            TypeName bodyType = getTypeName(content, jsonComposedSchemaType, null);

            ParameterSpec.Builder bodyParameterBuilder = ParameterSpec.builder(bodyType, "body");
            if (!mediaType.equals("multipart/form-data")) {
                AnnotationSpec RequestBodyAnnotation =
                    AnnotationSpec.builder(org.springframework.web.bind.annotation.RequestBody.class)
                        .build();
                bodyParameterBuilder.addAnnotation(RequestBodyAnnotation);
            }

            parameterSpecs.add(bodyParameterBuilder.build());
        }

        return parameterSpecs;
    }

    private TypeName getTypeName(Map<String, MediaType> content, ClassName jsonComposedSchemaType, String refName) {
        if (content == null) {
            return ClassName.get(Void.class);
        } else if (content.size() > 1) { // multiple content media types
            return ClassName.OBJECT;
        } else if (content.size() == 1) {
            Map.Entry<String, MediaType> entry = content.entrySet().iterator().next();
            String key = entry.getKey();
            if (key.equals("application/xml")) {
                return ClassName.get(String.class);
            } else if (key.equals("application/json")) {
                MediaType value = entry.getValue();
                // remember one of names and put them in comments (what about many content types + oneOf ?)
                Schema schema = value.getSchema();
                if (schema instanceof ComposedSchema) {
                    return jsonComposedSchemaType;
                } else if (schema.get$ref() != null) {
                    String schemaName = getSimpleName(schema.get$ref());
                    return ClassName.get(modelPackage, toClassName(schemaName));
                } else if (schema instanceof ObjectSchema) {
                    Objects.requireNonNull(refName);
                    return ClassName.get(modelPackage, toClassName(refName));
                } else {
                    throw new RuntimeException();
                }
            } else if (key.equals("multipart/form-data")) {
                String schemaName = getSimpleName(entry.getValue().getSchema().get$ref());
                return ClassName.get(modelPackage, toClassName(schemaName));
            } else {
                throw new RuntimeException(key);
            }
        } else {
            throw new RuntimeException();
        }
    }

    private String getSimpleName(String $ref) {
        Objects.requireNonNull($ref);
        return Paths.get($ref).getFileName().toString();
    }

    private TypeName toJavaTypeName(Schema schema) {
        return toJavaTypeName(schema, null);
    }

    private TypeName toJavaTypeName(Schema schema, String name) {
        if (schema.get$ref() != null) {
            String simpleName = getSimpleName(schema.get$ref());
            schema = api.getComponents().getSchemas().get(simpleName);
            return toJavaTypeName(schema, simpleName);
        }
        if (name == null && schema.getEnum() != null) {
            ClassName enumName = ClassName.get(modelPackage, toClassName(schemaName), toClassName(propertyName));
            nestedTypes.add(enumBuilder(enumName, schema).build());
            return enumName;
        }
        if (schema instanceof ObjectSchema || schema.getEnum() != null) {
//            if (name == null) { // anonymous schemas in Error400_AIS and similar
//                name = schemaName + "_" + propertyName;
//            }
            return ClassName.get(modelPackage, toClassName(name));
        }
        if (schema instanceof ByteArraySchema) {
            return ArrayTypeName.of(TypeName.BYTE);
        }
        if (schema instanceof ArraySchema) {
            Schema<?> items = ((ArraySchema) schema).getItems();
            return ParameterizedTypeName.get(ClassName.get(List.class), toJavaTypeName(items));
        }
        if (schema instanceof MapSchema) {
            return ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), toJavaTypeName((Schema) schema.getAdditionalProperties()));
        }
        if (schema instanceof ComposedSchema) {
            // todo oneOf comment
            return ClassName.get(Object.class);
        }
        return ClassName.get(toJavaType(schema));
    }

    private Type toJavaType(Schema schema) {
        if (schema instanceof StringSchema) {
            return String.class;
        } else if (schema instanceof BooleanSchema) {
            return Boolean.class;
        } else if (schema instanceof DateSchema) {
            return LocalDate.class;
        } else if (schema instanceof DateTimeSchema) {
            return OffsetDateTime.class;
        } else if (schema instanceof IntegerSchema) {
            return Integer.class;
        } else {
            throw new RuntimeException();
        }
    }

    private String toCamelCase(String name) {
        String[] words = name.split("[-_]");
        StringBuilder camelCaseName = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            camelCaseName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return camelCaseName.toString();
    }

    private void generateModels() {
        models = new LinkedHashMap<>();
        Map<String, Schema> responseSchemas = api.getComponents().getResponses().entrySet().stream()
            .filter(e -> {
                ApiResponse apiResponse = e.getValue();
                if (apiResponse == null) return false;
                Content content = apiResponse.getContent();
                if (content == null) return false;
                MediaType mediaType = content.get("application/json");
                if (mediaType == null) return false;
                return mediaType.getSchema() instanceof ObjectSchema;
            })
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getContent().get("application/json").getSchema()));
        Map<String, Schema> parameterSchemas = api.getComponents().getParameters().entrySet().stream()
            .filter(p -> p.getValue().getSchema() != null && (p.getValue().getIn().equals("path") || p.getValue().getIn().equals("query")))
            .collect(Collectors.toMap(Map.Entry::getKey, p -> p.getValue().getSchema()));
        Map<String, Schema> componentSchemas = api.getComponents().getSchemas();
        Map<String, Schema> combinedSchemas = new LinkedHashMap<>(componentSchemas);
        combinedSchemas.putAll(parameterSchemas);
        combinedSchemas.putAll(responseSchemas);
        for (Map.Entry<String, Schema> entry : combinedSchemas.entrySet()) {
            String name = entry.getKey();
            Schema schema = entry.getValue();
            if (!shouldBeSkipped(name)) {
                schemaName = name;
                TypeSpec.Builder typeSpecBuilder;
                if (schema instanceof ObjectSchema) {
                    ObjectSchema objectSchema = (ObjectSchema) schema;
                    List<FieldSpec> fieldSpecs = fieldSpecs(objectSchema.getProperties());
                    String className = toClassName(name);
                    typeSpecBuilder = TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC)
                        .addFields(fieldSpecs)
                        .addMethods(methods(fieldSpecs, className));
                    if (!nestedTypes.isEmpty()) {
                        nestedTypes.forEach(typeSpecBuilder::addType);
                        nestedTypes.clear();
                    }
                } else if (schema.getEnum() != null) {
                    typeSpecBuilder = enumBuilder(ClassName.get(modelPackage, toClassName(name)), schema);
                } else {
                    continue;
                }

                TypeSpec typeSpec = typeSpecBuilder.addAnnotation(generatedAnnotation())
                    .build();
                TypeSpec typeSpecCreatedEarlier = models.get(typeSpec.name);
                if (typeSpecCreatedEarlier == null) {
                    models.put(typeSpec.name, typeSpec);
                } else if (!typeSpecCreatedEarlier.equals(typeSpec)) {
                    if (typeSpec.kind == ENUM && typeSpecCreatedEarlier.kind == ENUM) {
                        System.out.println("Merging enum " + name + " into " + typeSpecCreatedEarlier.name);

                        TypeSpec.Builder builder = typeSpecCreatedEarlier.toBuilder();
                        typeSpec.enumConstants.forEach(builder::addEnumConstant);
                        models.put(typeSpec.name, builder.build());
                    } else {
                        System.out.println("Merging " + name + " into " + typeSpecCreatedEarlier.name);
                        TypeSpec.Builder builder = typeSpecCreatedEarlier.toBuilder();
                        typeSpec.fieldSpecs.forEach(f -> {
                            if (!typeSpecCreatedEarlier.fieldSpecs.contains(f)) {
                                builder.addField(f);
                            }
                        });
                        typeSpec.methodSpecs.forEach(m -> {
                            if (!typeSpecCreatedEarlier.methodSpecs.contains(m)) {
                                builder.addMethod(m);
                            }
                        });
                        models.put(typeSpec.name, builder.build());
                    }
                }
            }
        }

        for (TypeSpec typeSpec : models.values()) {
            JavaFile file = JavaFile.builder(modelPackage, typeSpec)
                .build();
            action.accept(file);
        }
    }

    private TypeSpec.Builder enumBuilder(ClassName enumName, Schema schema) {
        TypeSpec.Builder typeSpecBuilder;
        typeSpecBuilder = TypeSpec.enumBuilder(enumName)
            .addModifiers(Modifier.PUBLIC)
            .addField(String.class, "value", Modifier.PRIVATE)
            .addMethod(MethodSpec.constructorBuilder()
                .addParameter(String.class, "value")
                .addStatement("this.$N = $N", "value", "value")
                .build())
            .addMethod(MethodSpec.methodBuilder("fromValue")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(JsonCreator.class)
                .addParameter(String.class, "value")
                .returns(enumName)
                .beginControlFlow("for ($N e : $N.values())", enumName.simpleName(), enumName.simpleName())
                .beginControlFlow("if (e.value.equals(value))")
                .addStatement("return e")
                .endControlFlow()
                .endControlFlow()
                .addStatement("return null")
                .build())
            .addMethod(MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addAnnotation(JsonValue.class)
                .returns(String.class)
                .addStatement("return value")
                .build());
        StringSchema stringSchema = (StringSchema) schema;
        for (String enumValue : stringSchema.getEnum()) {
            TypeSpec enumValueWithParam = TypeSpec.anonymousClassBuilder("$S", enumValue)
                .build();
            if (!Character.isJavaIdentifierStart(enumValue.charAt(0))) {
                enumValue = "_" + enumValue;
            }
            typeSpecBuilder.addEnumConstant(enumValue.toUpperCase().replace('-', '_').replace('.', '_'), enumValueWithParam);
        }
        return typeSpecBuilder;
    }

    private AnnotationSpec generatedAnnotation() {
        return AnnotationSpec.builder(Generated.class)
            .addMember("value", "$S", TOOLNAME)
            .build();
    }

    private boolean shouldBeSkipped(String name) {
        return name.matches("Error\\d{3}_[A-Z]{3,}");
    }

    private String toClassName(String name) {
        String className;
        if (name.matches("tppMessage\\d{3}_[A-Z]{3,}")) {
            className = "TppMessage";
        } else if (name.matches("MessageCode\\d{3}_[A-Z]{3,}")) {
            className = "MessageCode";
        } else if (name.matches("Error\\d{3}_NG_[A-Z]{3,}")) {
            className = "ErrorResponse";
        } else {
            className = StringUtils.capitalize(toCamelCase(name));
        }
        return className;
    }

    private List<FieldSpec> fieldSpecs(Map<String, Schema> properties) {
        List<FieldSpec> fieldSpecs = new ArrayList<>(properties.size());
        properties.forEach((name, schema) -> {
            propertyName = name.startsWith("_") ? name.substring(1) : name;
            TypeName typeName = toJavaTypeName(schema);

            FieldSpec.Builder builder = FieldSpec.builder(typeName, propertyName, Modifier.PRIVATE);
            if (name.startsWith("_")) {
                builder.addAnnotation(AnnotationSpec.builder(JsonProperty.class)
                    .addMember("value", "$S", name)
                    .build());
            }
            FieldSpec fieldSpec = builder
                .build();
            fieldSpecs.add(fieldSpec);
        });
        return fieldSpecs;
    }

    private List<MethodSpec> methods(List<FieldSpec> fieldSpecs, String className) {
        List<MethodSpec> methodSpecs = gettersAndSetters(fieldSpecs);

        MethodSpec equals = MethodSpec.methodBuilder("equals")
            .addModifiers(Modifier.PUBLIC)
            .returns(boolean.class)
            .addAnnotation(Override.class)
            .addParameter(Object.class, "o")
            .addStatement("if (this == o) return true")
            .addStatement("if (o == null || getClass() != o.getClass()) return false")
            .addStatement("$N that = ($N) o", className, className)
            .addStatement(fieldSpecs.stream()
                .map(f -> String.format("Objects.equals(%s, that.%s)", f.name, f.name))
                .collect(Collectors.joining(" &&\n", "return ", "")))
            .build();
        MethodSpec hashCode = MethodSpec.methodBuilder("hashCode")
            .addModifiers(Modifier.PUBLIC)
            .returns(int.class)
            .addAnnotation(Override.class)
            .addStatement(fieldSpecs.stream()
                .map(f -> f.name)
                .collect(Collectors.joining(",\n", "return $T.hash(", ")")), Objects.class)
            .build();
        methodSpecs.add(equals);
        methodSpecs.add(hashCode);

        return methodSpecs;
    }

    private List<MethodSpec> gettersAndSetters(List<FieldSpec> fieldSpecs) {
        ArrayList<MethodSpec> methodSpecs = new ArrayList<>(fieldSpecs.size());
        for (FieldSpec fieldSpec : fieldSpecs) {
            MethodSpec getter = MethodSpec.methodBuilder("get" + StringUtils.capitalize(fieldSpec.name))
                .addModifiers(Modifier.PUBLIC)
                .returns(fieldSpec.type)
                .addStatement("return $N", fieldSpec)
                .build();
            MethodSpec setter = MethodSpec.methodBuilder("set" + StringUtils.capitalize(fieldSpec.name))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(fieldSpec.type, fieldSpec.name)
                .addStatement("this.$N = $N", fieldSpec, fieldSpec)
                .build();
            methodSpecs.add(getter);
            methodSpecs.add(setter);
        }
        return methodSpecs;
    }
}
