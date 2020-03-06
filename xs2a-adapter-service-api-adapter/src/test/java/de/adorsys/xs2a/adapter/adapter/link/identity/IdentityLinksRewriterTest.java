package de.adorsys.xs2a.adapter.adapter.link.identity;

import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Link;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityLinksRewriterTest {
    private LinksRewriter linksRewriter = new IdentityLinksRewriter();

    @Test
    void rewrite() {
        Map<String, Link> links = new HashMap<>();
        links.put("scaStatus", new Link("http://example.com/sca/status"));
        links.put("self", new Link("http://example.com/self"));

        Map<String, Link> rewrittenLinks = linksRewriter.rewrite(links);

        assertThat(rewrittenLinks).isEqualTo(links);
    }

    @Test
    void rewrite_emptyLinks() {
        Map<String, Link> links = new HashMap<>();

        Map<String, Link> rewrittenLinks = linksRewriter.rewrite(links);

        assertThat(rewrittenLinks).isEqualTo(links);
    }

    @Test
    void rewrite_nullLinks() {
        assertThat(linksRewriter.rewrite(null)).isNull();
    }
}
