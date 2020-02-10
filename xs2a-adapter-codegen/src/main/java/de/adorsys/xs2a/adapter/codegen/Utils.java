package de.adorsys.xs2a.adapter.codegen;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class Utils {
    public static void delete(TokenStreamRewriter rewriter, ParserRuleContext ctx) {
        TokenStream tokenStream = rewriter.getTokenStream();

        // handle dangling commas, trailing and leading
        Token next = nextToken(tokenStream, ctx.getStop());
        Token prev = prevToken(tokenStream, ctx.getStart());
        if (next.getText().equals(",")) {
            // remove preceding whitespace characters to avoid blank lines
            rewriter.delete(prev.getTokenIndex() + 1, next.getTokenIndex());
        } else if (next.getText().equals("}") && prev.getText().equals(",")) {
            rewriter.delete(prev, ctx.getStop());
        } else {
            // should not have empty objects
            throw new RuntimeException("Removing the only child:\n" + ctx.getText());
        }
    }

    public static Token prevToken(TokenStream tokenStream, Token token) {
        for (int i = token.getTokenIndex() - 1; i >= 0; i--) {
            token = tokenStream.get(i);
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                return token;
            }
        }
        return token;
    }

    public static Token nextToken(TokenStream tokenStream, Token token) {
        for (int i = token.getTokenIndex() + 1; i < tokenStream.size(); i++) {
            token = tokenStream.get(i);
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                return token;
            }
        }
        return token;
    }
}
