package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AdorsysAccountInformationServiceTest {
    private static final String ACCEPT_ALL = "*/*";
    private static final String ACCEPT_JSON = "application/json";
    private static final String ACCEPT_XML = "application/xml";

    @InjectMocks
    private AdorsysAccountInformationService accountInformationService;

    @Test
    void populatePostHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populatePostHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePostHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePostHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePostHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }

    @Test
    void populatePutHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populatePutHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePutHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePutHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePutHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }

    @Test
    void populateGetHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populateGetHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateGetHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateGetHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateGetHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }

    @Test
    void populateDeleteHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populateDeleteHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateDeleteHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateDeleteHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateDeleteHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }
}
