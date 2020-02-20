package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adapter.StandardPaymentProduct;
import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.impl.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.ScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditInitiateSinglePaymentResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditStartAuthorisationResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditPaymentScaStatusResponse;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.*;

import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class UnicreditPaymentInitiationService extends BasePaymentInitiationService {
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";
    private static final String SINGLE_PAYMENT_PAYMENT_SERVICE = "payments";

    private final UnicreditInitiateSinglePaymentResponseMapper initiateSinglePaymentResponseMapper = new UnicreditInitiateSinglePaymentResponseMapper();
    private final UnicreditStartAuthorisationResponseMapper startAuthorisationResponseMapper = new UnicreditStartAuthorisationResponseMapper();
    private final ScaStatusResponseMapper scaStatusResponseMapper = new ScaStatusResponseMapper();
    private final PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper = new PaymentInitiationScaStatusResponseMapper();

    public UnicreditPaymentInitiationService(String baseUri, HttpClient httpClient) {
        super(baseUri, httpClient);
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams,
                                                                            Object body) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct),
            body,
            requestHeaders,
            requestParams,
            PaymentInitiationRequestResponse.class,
            initiateSinglePaymentResponseMapper::modifyResponse);
    }

    @Override
    public Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct, String paymentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct, paymentId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<UnicreditStartScaProcessResponse> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(jsonResponseHandler(UnicreditStartScaProcessResponse.class));
        return new Response<>(response.getStatusCode(), startAuthorisationResponseMapper.modifyResponse(response.getBody()), response.getHeaders());
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        return updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, transactionAuthorisation, UnicreditPaymentScaStatusResponse.class, scaStatusResponseMapper::toScaStatusResponse);
    }

    @Override
    protected String getUpdatePaymentPsuDataUri(String paymentService, String paymentProduct, String paymentId, String authorisationId) {
        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId);
        return StringUri.withQuery(uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, authorisationId);
    }

    @Override
    public Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders) {
        if (!SINGLE_PAYMENT_PAYMENT_SERVICE.equals(paymentService)) {
            throw new UnsupportedOperationException();
        }

        if (!requestHeaders.isAcceptJson()) {
            throw new UnsupportedOperationException();
        }

        Response<PaymentInitiationStatus> response = this.getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders);
        return new Response<>(response.getStatusCode(), paymentInitiationScaStatusResponseMapper.toScaStatusResponse(response.getBody()), response.getHeaders());
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(ACCEPT_HEADER, ContentType.APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }
}
