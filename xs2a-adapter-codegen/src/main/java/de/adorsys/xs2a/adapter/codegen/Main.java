package de.adorsys.xs2a.adapter.codegen;

import com.squareup.javapoet.JavaFile;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<String, String> operationToInterface = Map.ofEntries(Map.entry("createConsent", "ConsentApi"),
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

            Map.entry("initiatePayment", "PaymentApi"),
            Map.entry("getPaymentInformation", "PaymentApi"),
            Map.entry("getPaymentInitiationScaStatus", "PaymentApi"),
            Map.entry("getPaymentInitiationStatus", "PaymentApi"),
            Map.entry("getPaymentInitiationAuthorisation", "PaymentApi"),
            Map.entry("startPaymentAuthorisation", "PaymentApi"),
            Map.entry("updatePaymentPsuData", "PaymentApi"));

        String modifiedSpec = filterOperations("src/main/resources/psd2-api 1.3.4 20190717v1.json", operationToInterface.keySet());
        modifiedSpec = addCustomParams(modifiedSpec);
        modifiedSpec = removeServers(modifiedSpec);

        Files.writeString(Paths.get("target", "xs2aapi.json"), modifiedSpec);

        ParseOptions options = new ParseOptions();
        options.setResolve(false);
        OpenAPI api = new OpenAPIV3Parser().readContents(modifiedSpec, null, options)
            .getOpenAPI();

        CodeGenerator codeGenerator = new CodeGenerator(api, Main::saveFile);
        codeGenerator.generateInterfaces(operationToInterface);
        codeGenerator.generateModels();
    }

    private static void saveFile(JavaFile file) {
        Path path = Paths.get("target/generated-sources", CodeGenerator.TOOLNAME, file.packageName.replace('.', '/'), file.typeSpec.name + ".java");
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
