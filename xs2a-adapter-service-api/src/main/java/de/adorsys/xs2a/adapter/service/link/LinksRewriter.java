package de.adorsys.xs2a.adapter.service.link;

import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.Map;

public interface LinksRewriter {

    Map<String, Link> rewrite(Map<String, Link> links);
}
