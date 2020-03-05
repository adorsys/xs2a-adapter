package de.adorsys.xs2a.adapter.adapter.link.identity;

import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.Map;

public class IdentityLinksRewriter implements LinksRewriter {

    @Override
    public Map<String, Link> rewrite(Map<String, Link> links) {
        return links;
    }
}
