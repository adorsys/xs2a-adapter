package de.adorsys.xs2a.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.gateway.api.PaymentApi;
import de.adorsys.xs2a.gateway.mapper.*;
import de.adorsys.xs2a.gateway.model.pis.PaymentInitiationSctWithStatusResponse;
import de.adorsys.xs2a.gateway.model.shared.Authorisations;
import de.adorsys.xs2a.gateway.model.shared.ScaStatusResponseTO;
import de.adorsys.xs2a.gateway.model.shared.StartScaprocessResponseTO;
import de.adorsys.xs2a.gateway.service.*;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController extends AbstractController implements PaymentApi {
    private final PaymentInitiationService paymentService;
    private final PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper;
    private final HeadersMapper headersMapper;

    private final SinglePaymentInformationMapper singlePaymentInformationMapper = Mappers.getMapper(SinglePaymentInformationMapper.class);
    private final PaymentInitiationStatusMapper paymentInitiationStatusMapper = Mappers.getMapper(PaymentInitiationStatusMapper.class);
    private final PaymentInitiationAuthorisationResponseMapper paymentInitiationAuthorisationResponseMapper = Mappers.getMapper(PaymentInitiationAuthorisationResponseMapper.class);

    public PaymentController(PaymentInitiationService paymentService,
                             PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper,
                             HeadersMapper headersMapper,
                             ObjectMapper objectMapper) {
        super(objectMapper);
        this.paymentService = paymentService;
        this.paymentInitiationScaStatusResponseMapper = paymentInitiationScaStatusResponseMapper;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<Object> initiatePayment(String paymentProduct, Map<String, String> headers, Object body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<PaymentInitiationRequestResponse> response = this.paymentService.initiateSinglePayment(paymentProduct, requestHeaders, body);

        return ResponseEntity.status(HttpStatus.CREATED)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(response.getResponseBody());
    }

    @Override
    public ResponseEntity<Object> initiatePayment(String paymentProduct, Map<String, String> headers, String body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<PaymentInitiationRequestResponse> response = paymentService.initiateSinglePayment(paymentProduct, requestHeaders, body);

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                .body(response.getResponseBody());
    }

    @Override
    public ResponseEntity<PaymentInitiationSctWithStatusResponse> getSinglePaymentInformation(String paymentProduct, String paymentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> response = this.paymentService.getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(singlePaymentInformationMapper.toPaymentInitiationSctWithStatusResponse(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<PaymentInitiationScaStatusResponse> response = this.paymentService.getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(paymentInitiationScaStatusResponseMapper.mapToScaStatusResponse(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<?> getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        if (requestHeaders.isAcceptJson()) {
            GeneralResponse<PaymentInitiationStatus> response = paymentService.getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders);

            return ResponseEntity.status(HttpStatus.OK)
                           .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                           .body(paymentInitiationStatusMapper.toPaymentInitiationStatusResponse200Json(response.getResponseBody()));
        }

        GeneralResponse<String> response = paymentService.getSinglePaymentInitiationStatusAsString(paymentProduct, paymentId, requestHeaders);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(response.getResponseBody());
    }

    @Override
    public ResponseEntity<Authorisations> getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<PaymentInitiationAuthorisationResponse> response = this.paymentService.getPaymentInitiationAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(paymentInitiationAuthorisationResponseMapper.toAuthorisations(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<StartScaprocessResponseTO> startSinglePaymentAuthorisation(String paymentProduct, String paymentId, Map<String, String> headers, ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication -> paymentService.startSinglePaymentAuthorisation(paymentProduct, paymentId, requestHeaders, updatePsuAuthentication)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                .body(startScaProcessResponseMapper.toStartScaprocessResponseTO((StartScaProcessResponse) response.getResponseBody()));
    }

    @Override
    public ResponseEntity<Object> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, Map<String, String> headers, ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication -> this.paymentService.updateConsentsPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, updatePsuAuthentication),
                (SelectPsuAuthenticationMethodHandler) selectPsuAuthenticationMethod -> this.paymentService.updateConsentsPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod),
                (TransactionAuthorisationHandler) transactionAuthorisation -> this.paymentService.updateConsentsPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, transactionAuthorisation)
        );

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(response.getResponseBody());
    }
}

