package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CrealogixServiceProviderTest {

    private static final HttpClientFactory clientFactory = mock(HttpClientFactory.class);
    private final CrealogixServiceProvider serviceProvider = new CrealogixServiceProvider();

    @BeforeEach
    void setUp() {
        String testConfigFile
            = getClass().getResource(File.separator + "crealogix.adapter.config.properties").getFile();
        AdapterConfig.setConfigFile(testConfigFile);
    }

    @AfterAll
    static void afterAll() {
        // to be sure default environment is re-set
        AdapterConfig.setConfigFile("");
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService =
            serviceProvider.getAccountInformationService(null, clientFactory, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(CrealogixAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService =
            serviceProvider.getPaymentInitiationService(null, clientFactory, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(CrealogixPaymentInitiationService.class);
    }
}
