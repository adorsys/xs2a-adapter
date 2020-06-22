package de.adorsys.xs2a.adapter.adapter.link.identity;

import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityLinksRewriterTest {
    private LinksRewriter linksRewriter = new IdentityLinksRewriter();

    @Test
    void rewrite() {
        Map<String, HrefType> links = new HashMap<>();
        HrefType scaStatus = new HrefType();
        scaStatus.setHref("http://example.com/sca/status");
        links.put("scaStatus", scaStatus);
        HrefType self = new HrefType();
        self.setHref("http://example.com/self");
        links.put("self", self);

        Map<String, HrefType> rewrittenLinks = linksRewriter.rewrite(links);

        assertThat(rewrittenLinks).isEqualTo(links);
    }

    @Test
    void rewrite_emptyLinks() {
        Map<String, HrefType> links = new HashMap<>();

        Map<String, HrefType> rewrittenLinks = linksRewriter.rewrite(links);

        assertThat(rewrittenLinks).isEqualTo(links);
    }

    @Test
    void rewrite_nullLinks() {
        assertThat(linksRewriter.rewrite(null)).isNull();
    }
}
