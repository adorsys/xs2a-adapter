package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.loader.mapper.Xs2aPsd2Mapper;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.function.Function;

class Xs2aPsd2PaymentInitiationServiceAdapter implements Psd2PaymentInitiationService {

    private final PaymentInitiationService paymentInitiationService;
    private final Xs2aPsd2Mapper mapper = Mappers.getMapper(Xs2aPsd2Mapper.class);

    Xs2aPsd2PaymentInitiationServiceAdapter(PaymentInitiationService paymentInitiationService) {
        this.paymentInitiationService = paymentInitiationService;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      PaymentInitiation body) {
        requireSinglePayment(paymentService);
        return paymentInitiationService.initiatePayment(paymentService.toString(),
            paymentProduct.toString(),
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(queryParameters),
            mapper.toSinglePaymentInitiationBody(body))
            .map(mapper::toPaymentInitiationRequestResponse);
    }

    private void requireSinglePayment(PaymentService paymentService) {
        if (paymentService != PaymentService.PAYMENTS) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      String body) {
        requireSinglePayment(paymentService);
        return paymentInitiationService.initiatePayment(paymentService.toString(),
            paymentProduct.toString(),
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(queryParameters),
            body)
            .map(mapper::toPaymentInitiationRequestResponse);
    }

    @Override
    public Response<Object> getPaymentInformation(PaymentService paymentService,
                                                  PaymentProduct paymentProduct,
                                                  String paymentId,
                                                  Map<String, String> queryParameters,
                                                  Map<String, String> headers) {
        requireSinglePayment(paymentService);
        return paymentInitiationService.getPaymentInformationAsString(paymentService.toString(),
            paymentProduct.toString(),
            paymentId,
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(queryParameters))
            .map(Function.identity());
    }

    @Override
    public Response<Object> getPaymentInitiationStatus(PaymentService paymentService,
                                                       PaymentProduct paymentProduct,
                                                       String paymentId,
                                                       Map<String, String> queryParameters,
                                                       Map<String, String> headers) {
        requireSinglePayment(paymentService);
        return paymentInitiationService.getPaymentInitiationStatusAsString(paymentService.toString(),
            paymentProduct.toString(),
            paymentId,
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(queryParameters))
            .map(Function.identity());
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) {
        return paymentInitiationService.getPaymentInitiationAuthorisation(paymentService.toString(),
            paymentProduct.toString(),
            paymentId,
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(queryParameters))
            .map(mapper::toAuthorisations);
    }

    @Override
    public Response<StartScaProcessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation body) {
        requireSinglePayment(paymentService);
        return paymentInitiationService.startPaymentAuthorisation(paymentService.toString(),
            paymentProduct.toString(),
            paymentId,
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(queryParameters),
            mapper.toUpdatePsuAuthentication(body))
            .map(mapper::toStartScaprocessResponse);
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     Map<String, String> queryParameters,
                                                                     Map<String, String> headers) {
        return paymentInitiationService.getPaymentInitiationScaStatus(paymentService.toString(),
            paymentProduct.toString(),
            paymentId,
            authorisationId,
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(queryParameters))
            .map(mapper::toScaStatusResponse);
    }

    @Override
    public Response<UpdateAuthorisationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      String authorisationId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      UpdateAuthorisation body) {
        if (body.getPsuData() != null) {
            return paymentInitiationService.updatePaymentPsuData(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                authorisationId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(queryParameters),
                mapper.toUpdatePsuAuthentication(body))
                .map(mapper::toUpdateAuthorisationResponse);
        }
        if (body.getAuthenticationMethodId() != null) {
            return paymentInitiationService.updatePaymentPsuData(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                authorisationId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(queryParameters),
                mapper.toSelectPsuAuthenticationMethod(body))
                .map(mapper::toUpdateAuthorisationResponse);
        }
        if (body.getScaAuthenticationData() != null) {
            return paymentInitiationService.updatePaymentPsuData(paymentService.toString(),
                paymentProduct.toString(),
                paymentId,
                authorisationId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(queryParameters),
                mapper.toTransactionAuthorisation(body))
                .map(mapper::toUpdateAuthorisationResponse);
        }
        throw new BadRequestException("Request body doesn't match any of the supported schemas");
    }
}
