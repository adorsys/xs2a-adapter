package de.adorsys.xs2a.adapter.codegen;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.HashSet;
import java.util.Set;

public class ReferencedComponentsFinder extends JSONBaseListener {
    private SimpleDirectedGraph<String, DefaultEdge> refs = new SimpleDirectedGraph<>(DefaultEdge.class);
    private boolean insidePaths;
    private Set<String> rootRefs = new HashSet<>();
    private Set<String> referencedComponents;

    @Override
    public void enterPaths(JSONParser.PathsContext ctx) {
        insidePaths = true;
    }

    @Override
    public void exitPaths(JSONParser.PathsContext ctx) {
        insidePaths = false;
    }

    @Override
    public void exitRef(JSONParser.RefContext ctx) {
        String foundReference = stripQuotes(ctx.STRING().getText());
        if (insidePaths) {
            rootRefs.add(foundReference);
            refs.addVertex(foundReference);
        } else {
            StringBuilder jsonPath = new StringBuilder();
            for (ParserRuleContext parent = ctx.getParent(); parent != null; parent = parent.getParent()) {
                if (parent instanceof JSONParser.JsonPairContext) {
                    JSONParser.JsonPairContext pairContext = (JSONParser.JsonPairContext) parent;
                    String quotedString = pairContext.STRING().getText();
                    String mappingKey = stripQuotes(quotedString);
                    jsonPath.insert(0, "/" + mappingKey);
                }
            }
            jsonPath.insert(0, "#");
            String referencer = jsonPath.toString();
            // #/components/schemas/schemaname/
            referencer = referencer.substring(0, nthIndexOf(jsonPath.toString(), 4, '/'));


            Graphs.addEdgeWithVertices(refs, referencer, foundReference);
        }
    }

    private int nthIndexOf(String string, int n, int ch) {
        int index = 0;
        for (int i = 0; i < n; i++) {
            index = string.indexOf(ch, index + 1);
            if (index == -1) {
                // "chosenScaMethod": {
                //        "$ref": "#/components/schemas/authenticationObject"
                //      },
                return string.length();
            }
        }
        return index;
    }

    private String stripQuotes(String quotedString) {
        return quotedString.substring(1, quotedString.length() - 1);
    }

    @Override
    public void exitJson(JSONParser.JsonContext ctx) {
        referencedComponents = new HashSet<>();
        BreadthFirstIterator iterator = new BreadthFirstIterator<>(refs, rootRefs);
        while (iterator.hasNext()) {
            referencedComponents.add((String) iterator.next());
        }
    }

    public Set<String> getReferencedComponents() {
        return referencedComponents;
    }
}
