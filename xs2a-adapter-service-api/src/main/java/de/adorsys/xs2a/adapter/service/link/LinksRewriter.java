package de.adorsys.xs2a.adapter.service.link;

import de.adorsys.xs2a.adapter.api.model.HrefType;

import java.util.Map;

public interface LinksRewriter {

    Map<String, HrefType> rewrite(Map<String, HrefType> links);
}
