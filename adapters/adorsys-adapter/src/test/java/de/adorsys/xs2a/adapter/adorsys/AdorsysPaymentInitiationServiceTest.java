package de.adorsys.xs2a.adapter.adorsys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdorsysPaymentInitiationServiceTest {

    private static final String DEFAULT_COUNTRY_CODE = "DE";

    private AdorsysPaymentInitiationService paymentInitiationService;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        paymentInitiationService = new AdorsysPaymentInitiationService(aspsp, httpClientFactory, List.of(), linksRewriter);
    }

    @Test
    void initiatePayment_payments_json_noCreditorAddressInitially() {
        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "", "");
        when(httpClient.post(anyString())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            RequestParams.empty(),
            new PaymentInitiationJson());

        verify(httpClient, times(1)).post(anyString());

        PaymentInitiationJson actualBody = null;
        try {
            actualBody = deserializeString(requestBuilder.body(), PaymentInitiationJson.class);
        } catch (JsonProcessingException e) {
            fail("failed to deserialize body string");
        }

        assertThat(actualBody)
            .extracting((body) -> Optional.ofNullable(body)
                .map(PaymentInitiationJson::getCreditorAddress)
                .map(Address::getCountry)
                .orElse(null))
            .isNotNull()
            .isEqualTo(DEFAULT_COUNTRY_CODE);
    }

    @Test
    void initiatePayment_payments_json_withCreditorAddress() {
        String countryCode = "UK";

        Address address = new Address();
        address.setCountry(countryCode);

        PaymentInitiationJson requestBody = new PaymentInitiationJson();
        requestBody.setCreditorAddress(address);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "", "");
        when(httpClient.post(any())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            RequestParams.empty(),
            requestBody);

        verify(httpClient, times(1)).post(any());

        PaymentInitiationJson actualBody = null;
        try {
            actualBody = deserializeString(requestBuilder.body(), PaymentInitiationJson.class);
        } catch (JsonProcessingException e) {
            fail("failed to deserialize body string");
        }

        assertThat(actualBody)
            .extracting((body) -> Optional.ofNullable(body)
                .map(PaymentInitiationJson::getCreditorAddress)
                .map(Address::getCountry)
                .orElse(null))
            .isNotNull()
            .isEqualTo(countryCode);
    }

    @Test
    void initiatePayment_periodic_json_noCreditorAddressInitially() {
        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "", "");
        when(httpClient.post(any())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        paymentInitiationService.initiatePayment(PaymentService.PERIODIC_PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            RequestParams.empty(),
            new PeriodicPaymentInitiationJson());

        verify(httpClient, times(1)).post(any());

        PeriodicPaymentInitiationJson actualBody = null;
        try {
            actualBody = deserializeString(requestBuilder.body(), PeriodicPaymentInitiationJson.class);
        } catch (JsonProcessingException e) {
            fail("failed to deserialize body string");
        }

        assertThat(actualBody)
            .extracting((body) -> Optional.ofNullable(body)
                .map(PeriodicPaymentInitiationJson::getCreditorAddress)
                .map(Address::getCountry)
                .orElse(null))
            .isNotNull()
            .isEqualTo(DEFAULT_COUNTRY_CODE);
    }

    private <T> T deserializeString(String value, Class<T> tClass) throws JsonProcessingException {
        return new ObjectMapper().readValue(value, tClass);
    }
}
