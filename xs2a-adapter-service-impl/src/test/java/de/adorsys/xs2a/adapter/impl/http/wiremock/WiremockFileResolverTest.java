package de.adorsys.xs2a.adapter.impl.http.wiremock;

import org.junit.jupiter.api.Test;

class WiremockFileResolverTest {

    @Test
    void getFileName() {
        String actualFileName = WiremockFileResolver.PIS_PAYMENTS_PAIN001_SCT_INITIATE_PAYMENT.getFileName();
    }
}
