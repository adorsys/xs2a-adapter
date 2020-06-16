package de.adorsys.xs2a.adapter.adapter.link.identity;

import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;

import java.util.Map;

public class IdentityLinksRewriter implements LinksRewriter {

    @Override
    public Map<String, HrefType> rewrite(Map<String, HrefType> links) {
        return links;
    }
}
