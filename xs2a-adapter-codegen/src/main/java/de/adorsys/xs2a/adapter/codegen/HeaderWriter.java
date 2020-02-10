package de.adorsys.xs2a.adapter.codegen;

import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HeaderWriter extends JSONBaseListener {
    private final TokenStreamRewriter rewriter;
    private final String paramRef;
    private final String paramSchema;

    private boolean insidePaths;

    public HeaderWriter(TokenStreamRewriter rewriter, Header... header) {
        this.rewriter = rewriter;
        paramRef = Arrays.stream(header).map(Header::getParamRef).collect(Collectors.joining());
        paramSchema = Arrays.stream(header).map(Header::getParamSchema).collect(Collectors.joining());
    }

    @Override
    public void enterPaths(JSONParser.PathsContext ctx) {
        insidePaths = true;
    }

    @Override
    public void exitPaths(JSONParser.PathsContext ctx) {
        insidePaths = false;
    }

    @Override
    public void enterJsonPair(JSONParser.JsonPairContext ctx) {
        if ("\"parameters\"".equals(ctx.STRING().toString())) {
            String insertString;
            if (insidePaths) {
                insertString = paramRef;
            } else {
                insertString = paramSchema + "\n    ";
            }
            String parameters = new StringBuilder(ctx.value().getText())
                .insert(1, insertString)
                .toString();
            rewriter.replace(ctx.value().getStart(), ctx.value().getStop(), parameters);
        }

    }
}
