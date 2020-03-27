package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.mapper.psd2.Psd2Mapper;
import de.adorsys.xs2a.adapter.rest.psd2.Psd2PaymentApi;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.Function;

@RestController
public class Psd2PaymentController implements Psd2PaymentApi {
    private final Psd2PaymentInitiationService paymentInitiationService;
    private final HeadersMapper headersMapper;
    private final Psd2Mapper mapper = Mappers.getMapper(Psd2Mapper.class);

    public Psd2PaymentController(Psd2PaymentInitiationService paymentInitiationService,
                                 HeadersMapper headersMapper) {
        this.paymentInitiationService = paymentInitiationService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers,
                                                                              PaymentInitiationTO body) {
        Response<PaymentInitiationRequestResponse> response =
            paymentInitiationService.initiatePayment(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                queryParameters,
                headers,
                mapper.toPaymentInitiation(body));
        return responseEntity(response, mapper::toPaymentInitiationRequestResponseTO);
    }

    private <T, U> ResponseEntity<U> responseEntity(Response<T> response, Function<T, U> bodyMapper) {
        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(bodyMapper.apply(response.getBody()));
    }

    @Override
    public ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers,
                                                                              String body) {
        Response<PaymentInitiationRequestResponse> response =
            paymentInitiationService.initiatePayment(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                queryParameters,
                headers,
                body);
        return responseEntity(response, mapper::toPaymentInitiationRequestResponseTO);
    }

    @Override
    public ResponseEntity<Object> getPaymentInformation(PaymentServiceTO paymentService,
                                                        PaymentProductTO paymentProduct,
                                                        String paymentId,
                                                        Map<String, String> queryParameters,
                                                        Map<String, String> headers) {
        Response<Object> response =
            paymentInitiationService.getPaymentInformation(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers);
        return responseEntity(response, mapper::toGetPaymentInformationResponseTO);
    }

    @Override
    public ResponseEntity<Object> getPaymentInitiationStatus(PaymentServiceTO paymentService,
                                                             PaymentProductTO paymentProduct,
                                                             String paymentId,
                                                             Map<String, String> queryParameters,
                                                             Map<String, String> headers) {
        Response<Object> response =
            paymentInitiationService.getPaymentInitiationStatus(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers);
        return responseEntity(response, mapper::toGetPaymentInitiationStatusResponseTO);
    }

    @Override
    public ResponseEntity<AuthorisationsTO> getPaymentInitiationAuthorisation(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              String paymentId,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers) {
        Response<Authorisations> response =
            paymentInitiationService.getPaymentInitiationAuthorisation(
                PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers);
        return responseEntity(response, mapper::toAuthorisationsTO);
    }

    @Override
    public ResponseEntity<StartScaProcessResponseTO> startPaymentAuthorisation(PaymentServiceTO paymentService,
                                                                               PaymentProductTO paymentProduct,
                                                                               String paymentId,
                                                                               Map<String, String> queryParameters,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        Response<StartScaProcessResponse> response =
            paymentInitiationService.startPaymentAuthorisation(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers,
                mapper.toUpdateAuthorisation(body));
        return responseEntity(response, mapper::toStartScaProcessResponseTO);
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getPaymentInitiationScaStatus(PaymentServiceTO paymentService,
                                                                             PaymentProductTO paymentProduct,
                                                                             String paymentId,
                                                                             String authorisationId,
                                                                             Map<String, String> queryParameters,
                                                                             Map<String, String> headers) {
        Response<ScaStatusResponse> response =
            paymentInitiationService.getPaymentInitiationScaStatus(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                authorisationId,
                queryParameters,
                headers);
        return responseEntity(response, mapper::toScaStatusResponseTO);
    }

    @Override
    public ResponseEntity<UpdateAuthorisationResponseTO> updatePaymentPsuData(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              String paymentId,
                                                                              String authorisationId,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers,
                                                                              UpdateAuthorisationTO body) {
        Response<UpdateAuthorisationResponse> response =
            paymentInitiationService.updatePaymentPsuData(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                authorisationId,
                queryParameters,
                headers,
                mapper.toUpdateAuthorisation(body));
        return responseEntity(response, mapper::toUpdateAuthorisationResponseTO);
    }
}
