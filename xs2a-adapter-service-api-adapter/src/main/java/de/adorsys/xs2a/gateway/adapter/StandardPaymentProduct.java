package de.adorsys.xs2a.gateway.adapter;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;

public enum StandardPaymentProduct implements PaymentProduct {
    SEPA_CREDIT_TRANSFERS("sepa-credit-transfers", MediaType.APPLICATION_JSON),
    PAIN_SEPA_CREDIT_TRANSFERS("pain.001-sepa-credit-transfers", MediaType.APPLICATION_XML);

    private final String slug;
    private final String mediaType;

    StandardPaymentProduct(String slug, String mediaType) {
        this.slug = slug;
        this.mediaType = mediaType;
    }

    public static StandardPaymentProduct fromSlug(String slug) {
        return Arrays.stream(values())
                .filter(p -> p.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public String getMediaType() {
        return mediaType;
    }
}
