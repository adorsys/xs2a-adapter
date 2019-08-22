package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adapter.PaymentProduct;
import de.adorsys.xs2a.adapter.adapter.StandardPaymentProduct;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.PaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.impl.mapper.ScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditInitiateSinglePaymentLinkMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditStartAuthorisationLinkMapper;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditPaymentScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class UnicreditPaymentInitiationService extends BasePaymentInitiationService {
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";

    private final UnicreditInitiateSinglePaymentLinkMapper initiateSinglePaymentLinkMapper = new UnicreditInitiateSinglePaymentLinkMapper();
    private final UnicreditStartAuthorisationLinkMapper startAuthorisationLinkMapper = new UnicreditStartAuthorisationLinkMapper();
    private final ScaStatusResponseMapper scaStatusResponseMapper = Mappers.getMapper(ScaStatusResponseMapper.class);

    public UnicreditPaymentInitiationService(String baseUri) {
        super(baseUri);
    }

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, RequestHeaders requestHeaders, Object body) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct), body, requestHeaders, PaymentInitiationRequestResponse.class, initiateSinglePaymentLinkMapper::modifyLinks);
    }

    @Override
    protected GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(PaymentProduct paymentProduct, String paymentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct.getSlug(), paymentId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        GeneralResponse<StartScaProcessResponse> response = httpClient.put(uri, body, headersMap, jsonResponseHandler(StartScaProcessResponse.class));
        return new GeneralResponse<>(response.getStatusCode(), startAuthorisationLinkMapper.modifyLinks(response.getResponseBody()), response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId);
        String uriWithQueryParam = StringUri.withQuery(uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        GeneralResponse<UnicreditPaymentScaStatusResponse> response = httpClient.put(uriWithQueryParam, body, headersMap, jsonResponseHandler(UnicreditPaymentScaStatusResponse.class));
        ScaStatusResponse scaStatusResponse = scaStatusResponseMapper.toScaStatusResponse(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), scaStatusResponse, response.getResponseHeaders());
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(ACCEPT_HEADER, APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);
        return headers;
    }
}
