/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.impl.link.identity;

import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.HrefType;
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
