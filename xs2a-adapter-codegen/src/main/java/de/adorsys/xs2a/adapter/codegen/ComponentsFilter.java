package de.adorsys.xs2a.adapter.codegen;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentsFilter extends JSONBaseListener {
    private Map<String, Set<String>> referencedComponents;
    private TokenStreamRewriter rewriter;
    private boolean insideComponents;
    private int level;
    private String componentType;
    private JSONParser.JsonPairContext lastComponent;

    public ComponentsFilter(Set<String> references, TokenStreamRewriter rewriter) {
        this.referencedComponents = references.stream()
            .map(ref -> ref.split("/"))
            .collect(Collectors.groupingBy(refElements -> refElements[refElements.length - 2], Collectors.mapping(refElements -> refElements[refElements.length - 1], Collectors.toSet())));
        this.rewriter = rewriter;
    }

    @Override
    public void enterJsonPair(JSONParser.JsonPairContext ctx) {
        if ("\"components\"".equals(ctx.STRING().getText())) {
            insideComponents = true;
            return;
        }

        if (insideComponents) {
            level++;
            if (level == 1) {
                componentType = stripQuotes(ctx.STRING().getText());
            } else if (level == 2 && referencedComponents.containsKey(componentType)) {
                Set<String> components = referencedComponents.get(componentType);
                if (!components.contains(stripQuotes(ctx.STRING().getText()))) {
                    Utils.delete(rewriter, ctx);
                } else {
                    lastComponent = ctx;
                }
            }
        }
    }

    private String stripQuotes(String quotedString) {
        return quotedString.substring(1, quotedString.length() - 1);
    }

    @Override
    public void exitJsonPair(JSONParser.JsonPairContext ctx) {
        if ("\"components\"".equals(ctx.STRING().getText())) {
            insideComponents = false;
            return;
        }
        if (insideComponents) {
            if (level == 1 && lastComponent != null) {
                Token nextToken = Utils.nextToken(rewriter.getTokenStream(), lastComponent.getStop());
                if (nextToken.getText().equals(",")) {
                    rewriter.delete(nextToken);
                }
                lastComponent = null;
            }
            level--;
        }
    }
}
