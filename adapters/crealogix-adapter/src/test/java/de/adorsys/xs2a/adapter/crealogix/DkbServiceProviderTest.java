package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DkbServiceProviderTest {

    private final DkbServiceProvider provider = new DkbServiceProvider();
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final Pkcs12KeyStore keyStore = mock(Pkcs12KeyStore.class);
    private final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    private final HttpLogSanitizer logSanitizer = mock(HttpLogSanitizer.class);
    private final Aspsp aspsp = new Aspsp();

    @Test
    void getAccountInformationService() {
        AccountInformationService service = provider.getAccountInformationService(aspsp, httpClientFactory, keyStore, linksRewriter, logSanitizer);

        assertThat(service)
            .isNotNull()
            .isExactlyInstanceOf(CrealogixAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService service = provider.getPaymentInitiationService(aspsp, httpClientFactory, keyStore, linksRewriter, logSanitizer);

        assertThat(service)
            .isNotNull()
            .isExactlyInstanceOf(CrealogixPaymentInitiationService.class);
    }

    @Test
    void getAdapterId() {
        String actual = provider.getAdapterId();
        assertThat(actual).isEqualTo("dkb-adapter");
    }

    @Test
    void getEmbeddedPreAuthorisationService() {
        EmbeddedPreAuthorisationService service = provider.getEmbeddedPreAuthorisationService(aspsp, httpClientFactory, logSanitizer);

        assertThat(service)
            .isNotNull()
            .isExactlyInstanceOf(CrealogixEmbeddedPreAuthorisationService.class);
    }
}
