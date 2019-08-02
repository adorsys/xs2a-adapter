package de.adorsys.xs2a.tpp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.PaymentApi;
import de.adorsys.xs2a.adapter.mapper.*;
import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.service.*;
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

    private final PaymentInitiationRequestResponseMapper paymentInitiationRequestResponseMapper = Mappers.getMapper(PaymentInitiationRequestResponseMapper.class);
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
    public ResponseEntity<PaymentInitationRequestResponse201TO> initiatePayment(PaymentServiceTO paymentService, PaymentProductTO paymentProduct,
                                                                                Map<String, String> headers, ObjectNode body) {
        return initiatePaymentInternal(paymentService, paymentProduct, headers, body);
    }

    private ResponseEntity<PaymentInitationRequestResponse201TO> initiatePaymentInternal(PaymentServiceTO paymentService,
                                                                                 PaymentProductTO paymentProduct,
                                                                                 Map<String, String> headers,
                                                                                 Object body) {
        requireSinglePayment(paymentService);
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<PaymentInitiationRequestResponse> response =
                this.paymentService.initiateSinglePayment(paymentProduct.toString(), requestHeaders, body);

        return ResponseEntity.status(HttpStatus.CREATED)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(paymentInitiationRequestResponseMapper.toPaymentInitationRequestResponse201TO(response.getResponseBody()));
    }

    private void requireSinglePayment(PaymentServiceTO paymentService) {
        if (paymentService != PaymentServiceTO.PAYMENTS) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public ResponseEntity<PaymentInitationRequestResponse201TO> initiatePayment(PaymentServiceTO paymentService, PaymentProductTO paymentProduct,
                                                  Map<String, String> headers, String body) {
        return initiatePaymentInternal(paymentService, paymentProduct, headers, body);
    }

    @Override
    public ResponseEntity<Object> getPaymentInformation(PaymentServiceTO paymentService, PaymentProductTO paymentProduct, String paymentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> response = this.paymentService.getSinglePaymentInformation(paymentProduct.toString(), paymentId, requestHeaders);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(singlePaymentInformationMapper.toPaymentInitiationSctWithStatusResponse(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getPaymentInitiationScaStatus(PaymentServiceTO paymentService, PaymentProductTO paymentProduct, String paymentId, String authorisationId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<PaymentInitiationScaStatusResponse> response = this.paymentService.getPaymentInitiationScaStatus(paymentService.toString(), paymentProduct.toString(), paymentId, authorisationId, requestHeaders);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(paymentInitiationScaStatusResponseMapper.mapToScaStatusResponse(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<Object> getPaymentInitiationStatus(PaymentServiceTO paymentService, PaymentProductTO paymentProduct, String paymentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        if (requestHeaders.isAcceptJson()) {
            GeneralResponse<PaymentInitiationStatus> response =
                    this.paymentService.getSinglePaymentInitiationStatus(paymentProduct.toString(), paymentId, requestHeaders);

            return ResponseEntity.status(HttpStatus.OK)
                           .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                           .body(paymentInitiationStatusMapper.toPaymentInitiationStatusResponse200Json(response.getResponseBody()));
        }

        GeneralResponse<String> response =
                this.paymentService.getSinglePaymentInitiationStatusAsString(paymentProduct.toString(), paymentId, requestHeaders);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(response.getResponseBody());
    }

    @Override
    public ResponseEntity<AuthorisationsTO> getPaymentInitiationAuthorisation(PaymentServiceTO paymentService, PaymentProductTO paymentProduct, String paymentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<PaymentInitiationAuthorisationResponse> response =
                this.paymentService.getPaymentInitiationAuthorisation(paymentService.toString(), paymentProduct.toString(), paymentId, requestHeaders);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(paymentInitiationAuthorisationResponseMapper.toAuthorisationsTO(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<StartScaprocessResponseTO> startPaymentAuthorisation(PaymentServiceTO paymentService, PaymentProductTO paymentProduct, String paymentId, Map<String, String> headers, ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication -> this.paymentService.startSinglePaymentAuthorisation(paymentProduct.toString(), paymentId, requestHeaders, updatePsuAuthentication)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                .body(startScaProcessResponseMapper.toStartScaprocessResponseTO((StartScaProcessResponse) response.getResponseBody()));
    }

    @Override
    public ResponseEntity<Object> updatePaymentPsuData(PaymentServiceTO paymentService, PaymentProductTO paymentProduct, String paymentId, String authorisationId, Map<String, String> headers, ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication -> this.paymentService.updatePaymentPsuData(paymentService.toString(), paymentProduct.toString(), paymentId, authorisationId, requestHeaders, updatePsuAuthentication),
                (SelectPsuAuthenticationMethodHandler) selectPsuAuthenticationMethod -> this.paymentService.updatePaymentPsuData(paymentService.toString(), paymentProduct.toString(), paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod),
                (TransactionAuthorisationHandler) transactionAuthorisation -> this.paymentService.updatePaymentPsuData(paymentService.toString(), paymentProduct.toString(), paymentId, authorisationId, requestHeaders, transactionAuthorisation)
        );

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(response.getResponseBody());
    }
}

