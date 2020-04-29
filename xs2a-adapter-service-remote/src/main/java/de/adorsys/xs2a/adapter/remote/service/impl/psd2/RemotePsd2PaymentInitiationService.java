package de.adorsys.xs2a.adapter.remote.service.impl.psd2;

import de.adorsys.xs2a.adapter.mapper.psd2.Psd2Mapper;
import de.adorsys.xs2a.adapter.remote.api.psd2.Psd2PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.service.mapper.ResponseHeadersMapper;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public class RemotePsd2PaymentInitiationService implements Psd2PaymentInitiationService {

    private final Psd2PaymentInitiationClient client;
    private final Psd2Mapper paymentInitiationMapper = Mappers.getMapper(Psd2Mapper.class);
    private final ResponseHeadersMapper responseHeadersMapper = Mappers.getMapper(ResponseHeadersMapper.class);

    public RemotePsd2PaymentInitiationService(Psd2PaymentInitiationClient client) {
        this.client = client;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      PaymentInitiation body) throws IOException {
        ResponseEntity<PaymentInitiationRequestResponseTO> response = client
            .initiatePayment(paymentService.toString(),
                paymentProduct.toString(),
                queryParameters,
                headers,
                paymentInitiationMapper.toPaymentInitiationTO(body));

        return new Response<>(response.getStatusCode().value(),
            paymentInitiationMapper.toPaymentInitiationRequestResponse(response.getBody()),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      String body) throws IOException {

        ResponseEntity<PaymentInitiationRequestResponseTO> response = client
            .initiatePayment(paymentService.toString(),
                paymentProduct.toString(),
                queryParameters,
                headers,
                body);

        return new Response<>(response.getStatusCode().value(),
            paymentInitiationMapper.toPaymentInitiationRequestResponse(response.getBody()),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    @Override
    public Response<Object> getPaymentInformation(PaymentService paymentService,
                                                  PaymentProduct paymentProduct,
                                                  String paymentId,
                                                  Map<String, String> queryParameters,
                                                  Map<String, String> headers) throws IOException {

        ResponseEntity<Object> response = client
            .getPaymentInformation(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                queryParameters,
                headers);

        return new Response<>(response.getStatusCode().value(),
            response.getBody(),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    @Override
    public Response<Object> getPaymentInitiationStatus(PaymentService paymentService,
                                                       PaymentProduct paymentProduct,
                                                       String paymentId,
                                                       Map<String, String> queryParameters,
                                                       Map<String, String> headers) throws IOException {
        ResponseEntity<Object> response = client
            .getPaymentInitiationStatus(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                queryParameters,
                headers);

        return new Response<>(response.getStatusCode().value(),
            response.getBody(),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) throws IOException {
        ResponseEntity<AuthorisationsTO> response = client
            .getPaymentInitiationAuthorisation(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                queryParameters,
                headers);

        return new Response<>(response.getStatusCode().value(),
            paymentInitiationMapper.toAuthorisations(response.getBody()),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    @Override
    public Response<StartScaProcessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation body) throws IOException {
        ResponseEntity<StartScaProcessResponseTO> response = client
            .startPaymentAuthorisation(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                queryParameters,
                headers,
                paymentInitiationMapper.toUpdateAuthorisationTO(body));

        return new Response<>(response.getStatusCode().value(),
            paymentInitiationMapper.toStartScaProcessResponse(response.getBody()),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     Map<String, String> queryParameters,
                                                                     Map<String, String> headers) throws IOException {
        ResponseEntity<ScaStatusResponseTO> response = client
            .getPaymentInitiationScaStatus(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                authorisationId,
                queryParameters,
                headers);

        return new Response<>(response.getStatusCode().value(),
            paymentInitiationMapper.toScaStatusResponse(response.getBody()),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    @Override
    public Response<UpdateAuthorisationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      String authorisationId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      UpdateAuthorisation body) throws IOException {
        ResponseEntity<UpdateAuthorisationResponseTO> response = client
            .updatePaymentPsuData(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                authorisationId,
                queryParameters,
                headers,
                paymentInitiationMapper.toUpdateAuthorisationTO(body));

        return new Response<>(response.getStatusCode().value(),
            paymentInitiationMapper.toUpdateAuthorisationResponse(response.getBody()),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }
}
