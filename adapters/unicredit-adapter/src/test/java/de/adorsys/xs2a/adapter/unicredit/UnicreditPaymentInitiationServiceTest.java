package de.adorsys.xs2a.adapter.unicredit;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static de.adorsys.xs2a.adapter.unicredit.UnicreditHeaders.DEFAULT_PSU_ID_TYPE;
import static de.adorsys.xs2a.adapter.unicredit.UnicreditHeaders.UCE_BANKING_GLOBAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UnicreditPaymentInitiationServiceTest {

    private static final String BASE_URL = "https://simulator-xs2a.db.com/p9is/DE/SB-DB";
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private static final String INITIATE_PAYMENT_URL = BASE_URL + "/v1/payments/sepa-credit-transfers";
    private static final String INITIATE_PERIODIC_PAYMENT_URL = BASE_URL + "/v1/periodic-payments/sepa-credit-transfers";
    private static final String PAYMENT_ID = "payment-id";
    private static final String PAYMENT_ID_URL = INITIATE_PAYMENT_URL + "/" + PAYMENT_ID;
    private static final String WRONG_PSU_ID_TYPE = "PSU_ID_TYPE";
    private static final String AUTHORISATION_ID = "authorisation-id";
    private static final String AUTHORISATION_URL = PAYMENT_ID_URL + "?authenticationCurrentNumber=" + AUTHORISATION_ID;
    private static final String TPP_REDIRECT_URI = "http://example.com";
    private static final String POST_METHOD = "POST";
    private static final String DEFAULT_COUNTRY_CODE = "DE";
    private static final Map<String, String> defaultHeaders = getHeadersMap(DEFAULT_PSU_ID_TYPE);
    private HttpClient httpClient;
    private UnicreditPaymentInitiationService paymentInitiationService;

    @BeforeEach
    void setUp() {
        httpClient = mock(HttpClient.class);
        LinksRewriter linksRewriter = mock(LinksRewriter.class);
        HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
        HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);

        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        paymentInitiationService = new UnicreditPaymentInitiationService(ASPSP, httpClientFactory, linksRewriter);
    }

    @Test
    void initiatePayment_wrongPsuIdTypeValue() {
        Map<String, String> headersMap = getHeadersMap(WRONG_PSU_ID_TYPE);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.fromMap(headersMap)));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
                                                 PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                 RequestHeaders.fromMap(headersMap),
                                                 RequestParams.empty(),
                                                 new PaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));
        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void initiatePayment_defaultPsuIdTypeValue() {
        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.fromMap(defaultHeaders)));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
                                                 PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                 RequestHeaders.fromMap(defaultHeaders),
                                                 RequestParams.empty(),
                                                 new PaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));
        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void initiatePayment_alternativeAcceptedPsuIdTypeValue() {
        Map<String, String> headersMap = getHeadersMap(UCE_BANKING_GLOBAL);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.fromMap(headersMap)));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
                                                 PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                 RequestHeaders.fromMap(headersMap),
                                                 RequestParams.empty(),
                                                 new PaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));
        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, UnicreditHeaders.UCE_BANKING_GLOBAL);
    }

    @Test
    void updatePaymentPsuData() {
        Request.Builder requestBuilder = spy(new RequestBuilderImpl(httpClient, "PUT", AUTHORISATION_URL));
        ScaStatusResponse statusResponse = new ScaStatusResponse();

        when(httpClient.put(anyString())).thenReturn(requestBuilder);
        doReturn(new Response<>(200,
                                statusResponse,
                                ResponseHeaders.fromMap(Collections.emptyMap()))).when(requestBuilder).send(any(), eq(Collections.emptyList()));

        paymentInitiationService.updatePaymentPsuData(PaymentService.PAYMENTS,
                                                      PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                      PAYMENT_ID,
                                                      AUTHORISATION_ID,
                                                      RequestHeaders.fromMap(Collections.emptyMap()),
                                                      RequestParams.empty(),
                                                      new TransactionAuthorisation());

        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void initiatePayment_payments_json_noCreditorAddressInitially() {
        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL))).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.fromMap(defaultHeaders),
            RequestParams.empty(),
            new PaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));

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

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL))).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.fromMap(defaultHeaders),
            RequestParams.empty(),
            requestBody);

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));

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
        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PERIODIC_PAYMENT_URL))).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        paymentInitiationService.initiatePayment(PaymentService.PERIODIC_PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.fromMap(defaultHeaders),
            RequestParams.empty(),
            new PeriodicPaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PERIODIC_PAYMENT_URL));

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

    private static Map<String, String> getHeadersMap(String uceBankingGlobal) {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, uceBankingGlobal);
        headersMap.put(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI);
        return headersMap;
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);
        return aspsp;
    }

    private <T> T deserializeString(String value, Class<T> tClass) throws JsonProcessingException {
        return new ObjectMapper().readValue(value, tClass);
    }
}
