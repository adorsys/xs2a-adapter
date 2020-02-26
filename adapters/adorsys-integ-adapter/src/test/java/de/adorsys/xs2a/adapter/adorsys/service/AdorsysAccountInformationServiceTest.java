package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AdorsysAccountInformationServiceTest {
    private static final String ACCEPT_ALL = "*/*";
    private static final String ACCEPT_JSON = "application/json";
    private static final String ACCEPT_XML = "application/xml";

    @InjectMocks
    private AdorsysAccountInformationService accountInformationService;

    @Test
    public void populatePostHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populatePostHeaders(new HashMap<>());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populatePostHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populatePostHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populatePostHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_XML);
    }

    @Test
    public void populatePutHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populatePutHeaders(new HashMap<>());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populatePutHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populatePutHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populatePutHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_XML);
    }

    @Test
    public void populateGetHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populateGetHeaders(new HashMap<>());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populateGetHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populateGetHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populateGetHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_XML);
    }

    @Test
    public void populateDeleteHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populateDeleteHeaders(new HashMap<>());

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populateDeleteHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populateDeleteHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_JSON);
    }

    @Test
    public void populateDeleteHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(RequestHeaders.ACCEPT)).isEqualTo(ACCEPT_XML);
    }
}
