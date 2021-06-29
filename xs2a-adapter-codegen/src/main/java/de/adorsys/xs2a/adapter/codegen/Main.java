package de.adorsys.xs2a.adapter.codegen;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.swagger.v3.oas.models.OpenAPI;
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

public class Main {
    private static final String CONSENT_API = "ConsentApi";
    private static final String ACCOUNT_API = "AccountApi";
    private static final String PAYMENT_API = "PaymentApi";

    private static final String OPENAPI_SPEC_DIR = "xs2a-adapter-rest-impl/src/main/resources/static";
    private static final String GENERATED_REST_API_DIR = "xs2a-adapter-generated-rest-api/src/main/java";
    private static final String GENERATED_API_DIR = "xs2a-adapter-generated-api/src/main/java";
    private static final Map<String, String> operationToInterface = Map.ofEntries(
        Map.entry("createConsent", CONSENT_API),
        Map.entry("getConsentInformation", CONSENT_API),
        Map.entry("getConsentStatus", CONSENT_API),
        Map.entry("startConsentAuthorisation", CONSENT_API),
        Map.entry("getConsentAuthorisation", CONSENT_API),
        Map.entry("getConsentScaStatus", CONSENT_API),
        Map.entry("updateConsentsPsuData", CONSENT_API),
        Map.entry("deleteConsent", CONSENT_API),

        Map.entry("getAccountList", ACCOUNT_API),
        Map.entry("getTransactionList", ACCOUNT_API),
        Map.entry("getTransactionDetails", ACCOUNT_API),
        Map.entry("getBalances", ACCOUNT_API),
        Map.entry("readAccountDetails", ACCOUNT_API),
        // card accounts
        Map.entry("getCardAccount", ACCOUNT_API),
        Map.entry("ReadCardAccount", ACCOUNT_API),
        Map.entry("getCardAccountTransactionList", ACCOUNT_API),
        Map.entry("getCardAccountBalances", ACCOUNT_API),


        Map.entry("initiatePayment", PAYMENT_API),
        Map.entry("getPaymentInformation", PAYMENT_API),
        Map.entry("getPaymentInitiationScaStatus", PAYMENT_API),
        Map.entry("getPaymentInitiationStatus", PAYMENT_API),
        Map.entry("getPaymentInitiationAuthorisation", PAYMENT_API),
        Map.entry("startPaymentAuthorisation", PAYMENT_API),
        Map.entry("updatePaymentPsuData", PAYMENT_API));


    public static void main(String[] args) throws IOException {

        String modifiedSpec = filterOperations("xs2a-adapter-codegen/src/main/resources/psd2-api 1.3.8 2020-11-06v1.json",
            operationToInterface.keySet());
        modifiedSpec = addCustomParams(modifiedSpec);
        modifiedSpec = removeServers(modifiedSpec);

        Files.writeString(Paths.get(OPENAPI_SPEC_DIR, "xs2aapi.json"), modifiedSpec);
        OpenAPI api = parseSpec(modifiedSpec);
        FileUtils.deleteDirectory(new File(GENERATED_REST_API_DIR));
        FileUtils.deleteDirectory(new File(GENERATED_API_DIR));
        new CodeGenerator(api, Main::saveFile, "de.adorsys.xs2a.adapter").generate(operationToInterface);
    }

    private static OpenAPI parseSpec(String json) {
        ParseOptions options = new ParseOptions();
        options.setResolve(false);
        return new OpenAPIV3Parser().readContents(json, null, options)
            .getOpenAPI();
    }

    private static void saveFile(JavaFile file) {
        String baseDir = file.typeSpec.kind == TypeSpec.Kind.INTERFACE ? GENERATED_REST_API_DIR : GENERATED_API_DIR;
        Path path = Paths.get(baseDir, file.packageName.replace('.', '/'), file.typeSpec.name + ".java");
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
