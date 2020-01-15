package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseAccountInformationServiceTest {

    private static final String BASE_URI = "baseUri";
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private BaseAccountInformationService informationService;

    @BeforeEach
    public void setUp() {
        informationService = new BaseAccountInformationService(ASPSP, null);
    }

    @Test
    public void getConsentBaseUri() {

        assertThat(informationService.getConsentBaseUri()).isEqualTo("baseUri/v1/consents");
    }

    @Test
    public void getAccountsBaseUri() {

        assertThat(informationService.getAccountsBaseUri()).isEqualTo("baseUri/v1/accounts");
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URI);
        return aspsp;
    }
}
