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

package de.adorsys.xs2a.adapter.impl.link.bg;

import de.adorsys.xs2a.adapter.impl.link.bg.template.LinksTemplate;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BerlinGroupLinksRewriterTest {
    private static final String HOST = "https://example.com";
    private static final String VERSION = "v1";
    private static final String CONSENT_ID = UUID.randomUUID().toString();
    private static final String AUTHORISATION_ID = UUID.randomUUID().toString();
    private static final String TRANSACTION_ID = UUID.randomUUID().toString();
    private static final String ACCOUNT_ID = UUID.randomUUID().toString();
    private static final String PAYMENT_SERVICE = "payments";
    private static final String PAYMENT_PRODUCT = "sepa-credit-transfers";
    private static final String PAYMENT_ID = UUID.randomUUID().toString();

    @Mock
    private LinksTemplate linksTemplate;

    private BerlinGroupLinksRewriter berlinGroupLinksRewriter;

    @BeforeEach
    void setUp() {
        berlinGroupLinksRewriter = new BerlinGroupLinksRewriter(linksTemplate, HOST, VERSION);
    }

    @Test
    void rewrite_failure_linksAreEmpty() {
        Map<String, HrefType> links = new HashMap<>();

        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(links);

        assertThat(rewrittenLinks).isEmpty();
    }

    @Test
    void rewrite_failure_linksAreNull() {
        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(null);

        assertThat(rewrittenLinks).isNull();
    }

    @Test
    void rewrite_failure_linksAreUnchangeable() {
        String unchangeableLinkName1 = "scaRedirect";
        HrefType unchangeableLink1 = new HrefType();
        unchangeableLink1.setHref("https://example.com/1");
        String unchangeableLinkName2 = "scaOAuth";
        HrefType unchangeableLink2 = new HrefType();
        unchangeableLink2.setHref("https://example.com/2");

        berlinGroupLinksRewriter = new BerlinGroupLinksRewriter(linksTemplate, HOST, VERSION) {
            @Override
            protected boolean linkUnchangeable(String linkName) {
                return unchangeableLinkName1.equals(linkName) || unchangeableLinkName2.equals(linkName);
            }
        };

        Map<String, HrefType> links = new HashMap<>();
        links.put(unchangeableLinkName1, unchangeableLink1);
        links.put(unchangeableLinkName2, unchangeableLink2);

        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(links);

        assertThat(rewrittenLinks).hasSize(links.size())
            .containsEntry(unchangeableLinkName1, unchangeableLink1)
            .containsEntry(unchangeableLinkName2, unchangeableLink2);
    }

    @Test
    void rewrite_failure_linksAreUnknown() {
        String unknownLinkName = "unknown";
        HrefType unknownLink = new HrefType();
        unknownLink.setHref("https://example.com/");

        Map<String, HrefType> links = new HashMap<>();
        links.put(unknownLinkName, unknownLink);

        when(linksTemplate.get(unknownLinkName)).thenReturn(Optional.empty());

        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(links);

        assertThat(rewrittenLinks).hasSize(links.size())
            .containsEntry(unknownLinkName, unknownLink);
    }

    @Test
    void rewrite_failure_noParamRetrieverForLinkTemplatePlaceholder() {
        String linkName = "linkName";
        HrefType link = new HrefType();
        link.setHref("https://example.com/");
        String template = "{host}/{unknown}";

        Map<String, HrefType> links = new HashMap<>();
        links.put(linkName, link);

        when(linksTemplate.get(linkName)).thenReturn(Optional.of(template));

        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(links);

        assertThat(rewrittenLinks).hasSize(links.size())
            .containsEntry(linkName, link);
    }

    @Test
    void rewrite_failure_emptyValueForPlaceholder() {
        String linkName = "linkName";
        HrefType link = new HrefType();
        link.setHref("https://example.com/v2/consents");
        String template = "{host}/{version}/consents/{consentId}";

        Map<String, HrefType> links = new HashMap<>();
        links.put(linkName, link);

        when(linksTemplate.get(linkName)).thenReturn(Optional.of(template));

        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(links);

        assertThat(rewrittenLinks).hasSize(links.size())
            .containsEntry(linkName, link);
    }

    @Test
    void rewrite_success() {
        String linkName1 = "linkName1";
        String linkHref1 = String.format(
            "https://adorsys.de/api/v42/consents/%s/authorisations/%s/transactions/%s",
            CONSENT_ID, AUTHORISATION_ID, TRANSACTION_ID
        );
        HrefType link1 = new HrefType();
        link1.setHref(linkHref1);
        String template1 = "{host}/{version}/consents/{consentId}/authorisations/{authorisationId}/transactions/{transactionId}";

        HrefType expectedRewrittenLink1 = new HrefType();
        expectedRewrittenLink1.setHref(String.format("%s/%s/consents/%s/authorisations/%s/transactions/%s",
            HOST, VERSION, CONSENT_ID, AUTHORISATION_ID, TRANSACTION_ID));

        String linkName2 = "linkName2";
        String linkHref2 = String.format(
            "https://adorsys.de/api/v42/%s/%s/%s",
            PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID
        );
        HrefType link2 = new HrefType();
        link2.setHref(linkHref2);
        String template2 = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}";

        HrefType expectedRewrittenLink2 = new HrefType();
        expectedRewrittenLink2.setHref(String.format("%s/%s/%s/%s/%s",
            HOST, VERSION, PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID));

        Map<String, HrefType> links = new HashMap<>();
        links.put(linkName1, link1);
        links.put(linkName2, link2);

        when(linksTemplate.get(linkName1)).thenReturn(Optional.of(template1));
        when(linksTemplate.get(linkName2)).thenReturn(Optional.of(template2));

        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(links);

        assertThat(rewrittenLinks).hasSize(links.size())
            .containsKeys(linkName1, linkName2);
        assertThat(rewrittenLinks.get(linkName1).getHref()).isEqualTo(expectedRewrittenLink1.getHref());
        assertThat(rewrittenLinks.get(linkName2).getHref()).isEqualTo(expectedRewrittenLink2.getHref());
    }

    @Test
    void rewrite_success_registerNewPlaceholder() {
        String newPlaceholder = "{newPlaceholder}";
        String newPlaceholderParamValue = UUID.randomUUID().toString();

        String linkName = "linkName1";
        String linkHref = String.format(
            "https://adorsys.de/api/v42/consents/%s/%s",
            CONSENT_ID, newPlaceholderParamValue
        );

        HrefType link = new HrefType();
        link.setHref(linkHref);
        String template = "{host}/{version}/consents/{consentId}/" + newPlaceholder;

        HrefType expectedRewrittenLink = new HrefType();
        expectedRewrittenLink.setHref(String.format("%s/%s/consents/%s/%s",
            HOST, VERSION, CONSENT_ID, newPlaceholderParamValue));

        Map<String, HrefType> links = new HashMap<>();
        links.put(linkName, link);

        when(linksTemplate.get(linkName)).thenReturn(Optional.of(template));

        berlinGroupLinksRewriter.registerPlaceholder(newPlaceholder, l -> Optional.of(newPlaceholderParamValue));
        Map<String, HrefType> rewrittenLinks = berlinGroupLinksRewriter.rewrite(links);

        assertThat(rewrittenLinks).hasSize(links.size())
            .containsKey(linkName);
        assertThat(rewrittenLinks.get(linkName).getHref()).isEqualTo(expectedRewrittenLink.getHref());
    }

    @Test
    void retrieveHost_success_existsInLink() {
        assertThat(berlinGroupLinksRewriter.retrieveHost("https://example.com"))
            .isEqualTo(Optional.of(HOST));
    }

    @Test
    void retrieveHost_failure_notExistsInLink() {
        assertThat(berlinGroupLinksRewriter.retrieveHost("anylink"))
            .isEqualTo(Optional.of(HOST));
    }

    @Test
    void retrieveVersion_success_existsInLink() {
        assertThat(berlinGroupLinksRewriter.retrieveVersion("https://example.com/v42"))
            .isEqualTo(Optional.of(VERSION));
    }

    @Test
    void retrieveVersion_failure_notExistsInLink() {
        assertThat(berlinGroupLinksRewriter.retrieveVersion("anylink"))
            .isEqualTo(Optional.of(VERSION));
    }

    @Test
    void retrieveConsentId_success_existsInLink() {
        String link = "https://example.com/consents/" + CONSENT_ID;

        Optional<String> actualConsentIdOptional = berlinGroupLinksRewriter.retrieveConsentId(link);

        assertThat(actualConsentIdOptional).contains(CONSENT_ID);
    }

    @Test
    void retrieveConsentId_failure_notExistsInLink() {
        String link = "https://example.com/consents/";

        Optional<String> actualConsentIdOptional = berlinGroupLinksRewriter.retrieveConsentId(link);

        assertThat(actualConsentIdOptional).isNotPresent();
    }

    @Test
    void retrieveAuthorisationId_success_existsInLink() {
        String link = "https://example.com/authorisations/" + AUTHORISATION_ID;

        Optional<String> actualAuthorisationIdOptional = berlinGroupLinksRewriter.retrieveAuthorisationId(link);

        assertThat(actualAuthorisationIdOptional).contains(AUTHORISATION_ID);
    }

    @Test
    void retrieveAuthorisationId_failure_notExistsInLink() {
        String link = "https://example.com/authorisations/";

        Optional<String> actualAuthorisationIdOptional = berlinGroupLinksRewriter.retrieveAuthorisationId(link);

        assertThat(actualAuthorisationIdOptional).isNotPresent();
    }

    @Test
    void retrieveAccountId_success_existsInLink() {
        String link = "https://example.com/accounts/" + ACCOUNT_ID;

        Optional<String> actualAccountIdOptional = berlinGroupLinksRewriter.retrieveAccountId(link);

        assertThat(actualAccountIdOptional).contains(ACCOUNT_ID);
    }

    @Test
    void retrieveAccountId_failure_notExistsInLink() {
        String link = "https://example.com/accounts/";

        Optional<String> actualAccountIdOptional = berlinGroupLinksRewriter.retrieveAccountId(link);

        assertThat(actualAccountIdOptional).isNotPresent();
    }

    @Test
    void retrieveTransactionId_success_existsInLink() {
        String link = "https://example.com/transactions/" + TRANSACTION_ID;

        Optional<String> actualTransactionIdOptional = berlinGroupLinksRewriter.retrieveTransactionId(link);

        assertThat(actualTransactionIdOptional).contains(TRANSACTION_ID);
    }

    @Test
    void retrieveTransactionId_failure_notExistsInLink() {
        String link = "https://example.com/transactions/";

        Optional<String> actualTransactionIdOptional = berlinGroupLinksRewriter.retrieveTransactionId(link);

        assertThat(actualTransactionIdOptional).isNotPresent();
    }

    @Test
    void retrievePaymentService_success_existsInLink() {
        String link = "https://example.com/v1/" + PAYMENT_SERVICE;

        Optional<String> actualPaymentServiceOptional = berlinGroupLinksRewriter.retrievePaymentService(link);

        assertThat(actualPaymentServiceOptional).contains(PAYMENT_SERVICE);
    }

    @Test
    void retrievePaymentService_failure_notExistsInLink() {
        String link = "https://example.com/v1/";

        Optional<String> actualPaymentServiceOptional = berlinGroupLinksRewriter.retrievePaymentService(link);

        assertThat(actualPaymentServiceOptional).isNotPresent();
    }

    @Test
    void retrievePaymentProduct_success_existsInLink() {
        String link = String.format(
            "https://example.com/v1/%s/%s",
            PAYMENT_SERVICE, PAYMENT_PRODUCT
        );

        Optional<String> actualPaymentProductOptional = berlinGroupLinksRewriter.retrievePaymentProduct(link);

        assertThat(actualPaymentProductOptional).contains(PAYMENT_PRODUCT);
    }

    @Test
    void retrievePaymentProduct_failure_notExistsInLink() {
        String link = "https://example.com/v1/";

        Optional<String> actualPaymentProductOptional = berlinGroupLinksRewriter.retrievePaymentProduct(link);

        assertThat(actualPaymentProductOptional).isNotPresent();
    }

    @Test
    void retrievePaymentId_success_existsInLink() {
        String link = String.format(
            "https://example.com/v1/%s/%s/%s",
            PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID
        );

        Optional<String> actualPaymentIdOptional = berlinGroupLinksRewriter.retrievePaymentId(link);

        assertThat(actualPaymentIdOptional).contains(PAYMENT_ID);
    }

    @Test
    void retrievePaymentId_failure_notExistsInLink() {
        String link = "https://example.com/v1/";

        Optional<String> actualPaymentIdOptional = berlinGroupLinksRewriter.retrievePaymentId(link);

        assertThat(actualPaymentIdOptional).isNotPresent();
    }
}
