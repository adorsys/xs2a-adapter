package de.adorsys.xs2a.tpp.controller.psd2;

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

import java.io.IOException;
import java.util.Map;

@RestController
public class Psd2PaymentController implements Psd2PaymentApi {

    private Psd2PaymentInitiationService service;
    private final Psd2Mapper psd2PaymentInitiationMapper = Mappers.getMapper(Psd2Mapper.class);
    private final HeadersMapper headersMapper;

    public Psd2PaymentController(Psd2PaymentInitiationService service, HeadersMapper headersMapper) {
        this.service = service;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers,
                                                                              PaymentInitiationTO body) throws IOException {
        Response<PaymentInitiationRequestResponse> response = service
            .initiatePayment(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                queryParameters,
                headers,
                psd2PaymentInitiationMapper.toPaymentInitiation(body));

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(psd2PaymentInitiationMapper.toPaymentInitiationRequestResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers,
                                                                              String body) throws IOException {
        Response<PaymentInitiationRequestResponse> response = service
            .initiatePayment(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                queryParameters,
                headers,
                body);

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(psd2PaymentInitiationMapper.toPaymentInitiationRequestResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<Object> getPaymentInformation(PaymentServiceTO paymentService,
                                                        PaymentProductTO paymentProduct,
                                                        String paymentId,
                                                        Map<String, String> queryParameters,
                                                        Map<String, String> headers) throws IOException {
        Response<Object> response = service
            .getPaymentInformation(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers);

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<Object> getPaymentInitiationStatus(PaymentServiceTO paymentService,
                                                             PaymentProductTO paymentProduct,
                                                             String paymentId,
                                                             Map<String, String> queryParameters,
                                                             Map<String, String> headers) throws IOException {
        Response<Object> response = service
            .getPaymentInitiationStatus(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers);

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<AuthorisationsTO> getPaymentInitiationAuthorisation(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              String paymentId,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers) throws IOException {
        Response<Authorisations> response = service
            .getPaymentInitiationAuthorisation(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers);

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(psd2PaymentInitiationMapper.toAuthorisationsTO(response.getBody()));
    }

    @Override
    public ResponseEntity<StartScaProcessResponseTO> startPaymentAuthorisation(PaymentServiceTO paymentService,
                                                                               PaymentProductTO paymentProduct,
                                                                               String paymentId,
                                                                               Map<String, String> queryParameters,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) throws IOException {
        Response<StartScaProcessResponse> response = service
            .startPaymentAuthorisation(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                queryParameters,
                headers,
                psd2PaymentInitiationMapper.toUpdateAuthorisation(body));

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(psd2PaymentInitiationMapper.toStartScaProcessResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getPaymentInitiationScaStatus(PaymentServiceTO paymentService,
                                                                             PaymentProductTO paymentProduct,
                                                                             String paymentId,
                                                                             String authorisationId,
                                                                             Map<String, String> queryParameters,
                                                                             Map<String, String> headers) throws IOException {
        Response<ScaStatusResponse> response = service
            .getPaymentInitiationScaStatus(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                authorisationId,
                queryParameters,
                headers);

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(psd2PaymentInitiationMapper.toScaStatusResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<UpdateAuthorisationResponseTO> updatePaymentPsuData(PaymentServiceTO paymentService,
                                                                              PaymentProductTO paymentProduct,
                                                                              String paymentId,
                                                                              String authorisationId,
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers,
                                                                              UpdateAuthorisationTO body) throws IOException {
        Response<UpdateAuthorisationResponse> response = service
            .updatePaymentPsuData(PaymentService.fromValue(paymentService.toString()),
                PaymentProduct.fromValue(paymentProduct.toString()),
                paymentId,
                authorisationId,
                queryParameters,
                headers,
                psd2PaymentInitiationMapper.toUpdateAuthorisation(body));

        return ResponseEntity.status(response.getStatusCode())
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(psd2PaymentInitiationMapper.toUpdateAuthorisationResponseTO(response.getBody()));
    }
}
