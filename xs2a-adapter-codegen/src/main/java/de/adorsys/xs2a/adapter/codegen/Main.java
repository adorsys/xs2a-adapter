package de.adorsys.xs2a.adapter.codegen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.javapoet.JavaFile;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class Main {

    private static final String OPENAPI_SPEC_DIR = "../xs2a-adapter-rest-impl/src/main/resources/static";
    private static final String GENERATED_CODE_DIR = "../xs2a-adapter-generated-rest-api/src/main/java";
    private static final Map<String, String> operationToInterface = Map.ofEntries(
        Map.entry("createConsent", "ConsentApi"),
        Map.entry("getConsentInformation", "ConsentApi"),
        Map.entry("getConsentStatus", "ConsentApi"),
        Map.entry("startConsentAuthorisation", "ConsentApi"),
        Map.entry("getConsentScaStatus", "ConsentApi"),
        Map.entry("updateConsentsPsuData", "ConsentApi"),
        Map.entry("deleteConsent", "ConsentApi"),

        Map.entry("getAccountList", "AccountApi"),
        Map.entry("getTransactionList", "AccountApi"),
        Map.entry("getTransactionDetails", "AccountApi"),
        Map.entry("getBalances", "AccountApi"),
        // card accounts
        Map.entry("getCardAccount", "AccountApi"),
        Map.entry("ReadCardAccount", "AccountApi"),
        Map.entry("getCardAccountTransactionList", "AccountApi"),
        Map.entry("getCardAccountBalances", "AccountApi"),


        Map.entry("initiatePayment", "PaymentApi"),
        Map.entry("getPaymentInformation", "PaymentApi"),
        Map.entry("getPaymentInitiationScaStatus", "PaymentApi"),
        Map.entry("getPaymentInitiationStatus", "PaymentApi"),
        Map.entry("getPaymentInitiationAuthorisation", "PaymentApi"),
        Map.entry("startPaymentAuthorisation", "PaymentApi"),
        Map.entry("updatePaymentPsuData", "PaymentApi"));

    private static final Map<String, String> psd2OperationToInterface = operationToInterface.entrySet().stream()
        .collect(toMap(e -> {
                switch (e.getKey()) {
                    case "getCardAccount":
                        return "getCardAccountList";
                    case "ReadCardAccount":
                        return "getCardAccountDetails";
                    default:
                        return e.getKey();
                }
            },
            e -> {
                switch (e.getValue()) {
                    case "PaymentApi":
                        return "Psd2PaymentApi";
                    case "AccountApi":
                    case "ConsentApi":
                        return "Psd2AccountInformationApi";
                    default:
                        throw new IllegalArgumentException(e.getValue());
                }
            }));


    public static void main(String[] args) throws IOException {

        String modifiedSpec = filterOperations("src/main/resources/psd2-api 1.3.6 20200306v1.json", operationToInterface.keySet());
        modifiedSpec = addCustomParams(modifiedSpec);
        modifiedSpec = removeServers(modifiedSpec);

        Files.writeString(Paths.get(OPENAPI_SPEC_DIR, "xs2aapi.json"), modifiedSpec);
        OpenAPI api = parseSpec(modifiedSpec);
        FileUtils.deleteDirectory(new File(GENERATED_CODE_DIR));
        new CodeGenerator(api, Main::saveFile, "de.adorsys.xs2a.adapter").generate(operationToInterface);

        OpenAPI psd2api = parseSpec(modifiedSpec);
        psd2api.info(new Info()
            .title("PSD2 API")
            .version("0.0.1"))
            .externalDocs(null)
            .servers(null)
            .tags(null)
            .components(psd2api.getComponents()
                .extensions(null))
            .paths(psd2Paths(psd2api.getPaths()))
            .schema("accountReport", psd2api.getComponents().getSchemas()
                .get("accountReport")
                .addProperties("info", new Schema().$ref("#/components/schemas/transactionList")))
            .schema("accountReference", psd2api.getComponents().getSchemas()
                .get("accountReference")
                .addProperties("bic", new StringSchema()))
            .schema("transactionDetails", psd2api.getComponents().getSchemas()
                .get("transactionDetails")
                .addProperties("executionDateTime", new DateTimeSchema())
                .addProperties("transactionType", new StringSchema()))
            .schema("_linksPaymentInitiation", psd2api.getComponents().getSchemas()
                .get("_linksPaymentInitiation")
                .addProperties("delete", new Schema().$ref("#/components/schemas/hrefType")))
            .schema("paymentInitiationWithStatusResponse", psd2api.getComponents().getSchemas()
                .get("paymentInitiationWithStatusResponse")
                .addProperties("chargeBearer", new StringSchema())
                .addProperties("remittanceInformationUnstructured", new StringSchema())
                .addProperties("clearingSystemMemberIdentification",
                    new Schema().$ref("#/components/schemas/clearingSystemMemberIdentification"))
                .addProperties("debtorName", new StringSchema())
                .addProperties("debtorAgent", new StringSchema())
                .addProperties("instructionPriority", new StringSchema())
                .addProperties("serviceLevelCode", new StringSchema())
                .addProperties("localInstrumentCode", new StringSchema())
                .addProperties("categoryPurposeCode", new StringSchema())
                .addProperties("requestedExecutionDate", new DateSchema()))
            .schema("clearingSystemMemberIdentification", new ObjectSchema()
                .addProperties("clearingSystemIdentificationCode", new StringSchema())
                .addProperties("memberIdentification", new StringSchema()));
        psd2api.getPaths().get("/card-accounts/{account-id}")
            .readOperationsMap()
            .get(PathItem.HttpMethod.GET)
            .operationId("getCardAccountDetails");
        psd2api.getPaths().get("/card-accounts")
            .readOperationsMap()
            .get(PathItem.HttpMethod.GET)
            .operationId("getCardAccountList");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        Files.writeString(Paths.get(OPENAPI_SPEC_DIR, "psd2api.json"),
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(psd2api));
        new CodeGenerator(psd2api,
                Main::saveFile,
                "de.adorsys.xs2a.adapter.rest.psd2",
                true,
                Main::renamePsd2Model,
                Map.of("updateConsentsPsuData", "UpdateAuthorisationTO",
                    "startConsentAuthorisation", "UpdateAuthorisationTO",
                    "startPaymentAuthorisation", "UpdateAuthorisationTO",
                    "updatePaymentPsuData", "UpdateAuthorisationTO",
                    "initiatePayment", "PaymentInitiationTO"),
                Map.of("updateConsentsPsuData", "UpdateAuthorisationResponseTO",
                    "updatePaymentPsuData", "UpdateAuthorisationResponseTO"))
            .generate(psd2OperationToInterface);

    }

    private static String renamePsd2Model(String name) {
        if (name.equals("OK200CardAccountDetails")) return "CardAccountDetailsResponse";
        if (name.equals("OK200TransactionDetails")) return "TransactionDetailsResponse";
        if (name.equals("StartScaprocessResponse")) return "StartScaProcessResponse";
        if (name.equals("PaymentInitationRequestResponse201")) return "PaymentInitiationRequestResponse";
        // merge update authorisation response models
        if (name.equals("UpdatePsuIdenticationResponse")) return "UpdateAuthorisationResponse";
        if (name.equals("UpdatePsuAuthenticationResponse")) return "UpdateAuthorisationResponse";
        if (name.equals("SelectPsuAuthenticationMethodResponse")) return "UpdateAuthorisationResponse";
        // merge update authorisation request models
        if (name.equals("UpdatePsuAuthentication")) return "UpdateAuthorisation";
        if (name.equals("SelectPsuAuthenticationMethod")) return "UpdateAuthorisation";
        if (name.equals("TransactionAuthorisation")) return "UpdateAuthorisation";
        // merge initiate payment request models
        if (name.equals("BulkPaymentInitiationJson")) return "PaymentInitiation";
        if (name.equals("PeriodicPaymentInitiationJson")) return "PaymentInitiation";
        return name.replace("OK", "").replace("200", "").replace("201", "").replace("2XX", "").replace("Json", "");
    }

    private static io.swagger.v3.oas.models.Paths psd2Paths(io.swagger.v3.oas.models.Paths paths) {
        io.swagger.v3.oas.models.Paths psd2Paths = new io.swagger.v3.oas.models.Paths();
        for (Map.Entry<String, PathItem> e : paths.entrySet()) {
            assert e.getKey().startsWith("/v1");
            e.getValue().readOperations().
                forEach(o -> o.getTags().remove("Common Services"));
            psd2Paths.addPathItem(e.getKey().substring(3), e.getValue());
        }
        return psd2Paths;
    }

    private static OpenAPI parseSpec(String json) {
        ParseOptions options = new ParseOptions();
        options.setResolve(false);
        return new OpenAPIV3Parser().readContents(json, null, options)
            .getOpenAPI();
    }

    private static void saveFile(JavaFile file) {
        Path path = Paths.get(GENERATED_CODE_DIR, file.packageName.replace('.', '/'), file.typeSpec.name + ".java");
        Path dir = path.getParent();
        try {
            Files.createDirectories(dir);
            Files.writeString(path, file.toString(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String filterOperations(String jsonFileName, Set<String> operationIds) throws IOException {
        JSONLexer lexer = new JSONLexer(CharStreams.fromFileName(jsonFileName));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        JSONParser.JsonContext json = parser.json();
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokens);
        OperationsFilter listener = new OperationsFilter(operationIds, rewriter);
        parseTreeWalker.walk(listener, json);
        return filterComponents(rewriter.getText());
    }

    private static String filterComponents(String jsonString) {
        JSONLexer lexer = new JSONLexer(CharStreams.fromString(jsonString));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        JSONParser.JsonContext json = parser.json();
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        ReferencedComponentsFinder finder = new ReferencedComponentsFinder();
        parseTreeWalker.walk(finder, json);
        Set<String> referencedComponents = finder.getReferencedComponents();
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokens);
        ComponentsFilter filter = new ComponentsFilter(referencedComponents, rewriter);
        parseTreeWalker.walk(filter, json);
        return rewriter.getText();
    }

    private static String addCustomParams(String jsonString) {
        JSONLexer lexer = new JSONLexer(CharStreams.fromString(jsonString));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        JSONParser.JsonContext json = parser.json();
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokens);
        ParamWriter paramWriter = new ParamWriter(rewriter,
            new Header("X-GTW-ASPSP-ID", "ASPSP ID in the registry for the request routing"),
            new Header("X-GTW-Bank-Code", "Bank code of bank to which the request addressed"),
            new Header("X-GTW-BIC", "Business Identifier Code for the request routing"),
            new Header("X-GTW-IBAN", "IBAN for the request routing"),
            new AdditionalQueryParams("additionalQueryParameters", "Additional query parameters"));
        parseTreeWalker.walk(paramWriter, json);
        return rewriter.getText();
    }

    private static String removeServers(String jsonString) {
        return jsonString.replaceFirst(",\\s+\"servers\"\\s*:\\s*\\[[^]]+]", "");
    }
}
