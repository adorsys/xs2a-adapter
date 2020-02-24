package de.adorsys.xs2a.adapter.codegen;

import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ParamWriter extends JSONBaseListener {
    private final TokenStreamRewriter rewriter;
    private final String paramRef;
    private final String paramSchema;

    private boolean insidePaths;

    public ParamWriter(TokenStreamRewriter rewriter, Param... param) {
        this.rewriter = rewriter;
        paramRef = Arrays.stream(param).map(Param::getParamRef).collect(Collectors.joining());
        paramSchema = Arrays.stream(param).map(Param::getParamSchema).collect(Collectors.joining());
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
