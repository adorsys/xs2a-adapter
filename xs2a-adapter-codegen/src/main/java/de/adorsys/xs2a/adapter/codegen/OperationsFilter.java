package de.adorsys.xs2a.adapter.codegen;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.HashSet;
import java.util.Set;

public class OperationsFilter extends JSONBaseListener {
    private Set<String> operationIds;
    private TokenStreamRewriter rewriter;
    private Set<JSONParser.OperationIdContext> dead = new HashSet<>();
    private Set<JSONParser.OperationIdContext> alive = new HashSet<>();
    private JSONParser.PathItemContext lastPathItem; // tracking to remove trailing comma

    public OperationsFilter(Set<String> operationIds, TokenStreamRewriter rewriter) {
        this.rewriter = rewriter;
        this.operationIds = operationIds;
    }

    @Override
    public void enterPathItem(JSONParser.PathItemContext ctx) {
        dead.clear();
        alive.clear();
    }

    @Override
    public void exitOperationId(JSONParser.OperationIdContext ctx) {
        String quotedString = ctx.STRING().getText();
        if (operationIds.contains(quotedString.substring(1, quotedString.length() - 1))) {
            alive.add(ctx);
        } else {
            dead.add(ctx);
        }
    }

    @Override
    public void exitPathItem(JSONParser.PathItemContext ctx) {
        if (alive.isEmpty()) {
            Utils.delete(rewriter, ctx);
        } else {
            dead.forEach(operationIdContext -> {
                JSONParser.JsonPairContext pair = (JSONParser.JsonPairContext) operationIdContext.getParent()
                    .getParent()
                    .getParent();
                Utils.delete(rewriter, pair);
            });
            lastPathItem = ctx;
        }
    }

    @Override
    public void exitPaths(JSONParser.PathsContext ctx) {
        Token nextToken = Utils.nextToken(rewriter.getTokenStream(), lastPathItem.getStop());
        if (nextToken.getText().equals(",")) {
            rewriter.delete(nextToken);
        }
    }
}

// todo if last and next to last comma separated items removed need extra cleanup for operations
