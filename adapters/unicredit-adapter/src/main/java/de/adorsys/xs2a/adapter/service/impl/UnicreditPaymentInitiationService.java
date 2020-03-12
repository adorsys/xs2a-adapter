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
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.validation.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class UnicreditPaymentInitiationService extends BasePaymentInitiationService {
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";
    private static final String SINGLE_PAYMENT_PAYMENT_SERVICE = "payments";

    private final UnicreditInitiateSinglePaymentResponseMapper initiateSinglePaymentResponseMapper = new UnicreditInitiateSinglePaymentResponseMapper();
    private final UnicreditStartAuthorisationResponseMapper startAuthorisationResponseMapper = new UnicreditStartAuthorisationResponseMapper();
    private final ScaStatusResponseMapper scaStatusResponseMapper = new ScaStatusResponseMapper();
    private final PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper = new PaymentInitiationScaStatusResponseMapper();

    public UnicreditPaymentInitiationService(String baseUri,
                                             HttpClient httpClient,
                                             LinksRewriter linksRewriter) {
        super(baseUri, httpClient, linksRewriter);
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
    public Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams,
                                                                             UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct, paymentId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<UnicreditStartScaProcessResponse> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(jsonResponseHandler(UnicreditStartScaProcessResponse.class));
        return new Response<>(response.getStatusCode(), startAuthorisationResponseMapper.modifyResponse(response.getBody()), response.getHeaders());
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                            String paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            transactionAuthorisation,
            UnicreditPaymentScaStatusResponse.class,
            scaStatusResponseMapper::toScaStatusResponse);
    }

    @Override
    protected String getUpdatePaymentPsuDataUri(String paymentService, String paymentProduct, String paymentId, String authorisationId) {
        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId);
        return StringUri.withQuery(uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, authorisationId);
    }

    @Override
    public Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                                      String paymentProduct,
                                                                                      String paymentId,
                                                                                      String authorisationId,
                                                                                      RequestHeaders requestHeaders,
                                                                                      RequestParams requestParams) {
        List<ValidationError> validationErrors = validateGetPaymentInitiationScaStatus(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams);
        if (!validationErrors.isEmpty()) {
            throw new RequestValidationException(validationErrors);
        }


        Response<PaymentInitiationStatus> response = this.getSinglePaymentInitiationStatus(paymentProduct,
            paymentId,
            requestHeaders,
            requestParams);
        return new Response<>(response.getStatusCode(),
            paymentInitiationScaStatusResponseMapper.toScaStatusResponse(response.getBody()),
            response.getHeaders());
    }

    @Override
    public List<ValidationError> validateGetPaymentInitiationScaStatus(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       String authorisationId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        ArrayList<ValidationError> errors = new ArrayList<>(2);
        if (!SINGLE_PAYMENT_PAYMENT_SERVICE.equals(paymentService)) {
            errors.add(new ValidationError(ValidationError.Code.NOT_SUPPORTED,
                "paymentService",
                "'" + paymentService + "' is not a supported payment service"));
        }

        if (!requestHeaders.isAcceptJson()) {
            errors.add(new ValidationError(ValidationError.Code.NOT_SUPPORTED,
                RequestHeaders.ACCEPT,
                "'" + requestHeaders.get(RequestHeaders.ACCEPT) + "' is not allowed")); // why?
        }
        return Collections.unmodifiableList(errors);
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
