package de.adorsys.xs2a.adapter.impl.link.identity;

import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.HrefType;

import java.util.Map;

public class IdentityLinksRewriter implements LinksRewriter {

    @Override
    public Map<String, HrefType> rewrite(Map<String, HrefType> links) {
        return links;
    }
}
