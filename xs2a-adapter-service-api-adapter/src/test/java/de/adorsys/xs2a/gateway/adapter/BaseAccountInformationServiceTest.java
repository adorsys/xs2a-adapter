package de.adorsys.xs2a.gateway.adapter;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseAccountInformationServiceTest {

    private static final String BASE_URI = "baseUri/v1/";
    private BaseAccountInformationService informationService;

    @Before
    public void setUp() {
        informationService = new BaseAccountInformationService(BASE_URI);
    }

    @Test
    public void getConsentBaseUri() {

        assertThat(informationService.getConsentBaseUri()).isEqualTo("baseUri/v1/consents");
    }

    @Test
    public void getAccountsBaseUri() {

        assertThat(informationService.getAccountsBaseUri()).isEqualTo("baseUri/v1/accounts");
    }
}