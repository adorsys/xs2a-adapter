package de.adorsys.xs2a.adapter.service.impl.service;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UnicreditLinkBuilderServiceTest {
    private static final String URI_WITHOUT_QUERY_PARAM = "test/link";
    private static final String AUTHORISATIONS_PATH = "authorisations";
    private static final String EXPECTED_START_AUTHORISATION_URI = String.format("%s/%s", URI_WITHOUT_QUERY_PARAM, AUTHORISATIONS_PATH);
    private static final String EXPECTED_UPDATE_PSU_DATA_URI_WITHOUT_QUERY_PARAM = "test/link";
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";
    private static final String AUTHENTICATION_CURRENT_NUMBER_VALUE = "12345";
    private static final String URI_WITH_QUERY_PARAM = String.format("test/link?%s=%s", AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, AUTHENTICATION_CURRENT_NUMBER_VALUE);
    private static final String EXPECTED_UPDATE_PSU_DATA_URI_WITH_QUERY_PARAM = String.format("test/link/%s/%s", AUTHORISATIONS_PATH, AUTHENTICATION_CURRENT_NUMBER_VALUE);

    private UnicreditLinkBuilderService linkBuilderService;

    @Before
    public void setUp() {
        linkBuilderService = new UnicreditLinkBuilderService();
    }

    @Test
    public void buildStartAuthorisationUri_Success() {
        String startAuthorisationUri = linkBuilderService.buildStartAuthorisationUri(URI_WITHOUT_QUERY_PARAM);
        assertThat(startAuthorisationUri).isEqualTo(EXPECTED_START_AUTHORISATION_URI);
    }

    @Test
    public void buildUpdatePsuDataUri_Success_UriWithoutQueryParam() {
        String updatePsuDataUri = linkBuilderService.buildUpdatePsuDataUri(URI_WITHOUT_QUERY_PARAM);
        assertThat(updatePsuDataUri).isEqualTo(EXPECTED_UPDATE_PSU_DATA_URI_WITHOUT_QUERY_PARAM);
    }

    @Test
    public void buildUpdatePsuDataUri_Success_UriWithQueryParam() {
        String updatePsuDataUri = linkBuilderService.buildUpdatePsuDataUri(URI_WITH_QUERY_PARAM);
        assertThat(updatePsuDataUri).isEqualTo(EXPECTED_UPDATE_PSU_DATA_URI_WITH_QUERY_PARAM);
    }
}
