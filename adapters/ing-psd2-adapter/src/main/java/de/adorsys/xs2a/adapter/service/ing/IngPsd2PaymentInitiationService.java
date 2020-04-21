package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ing.internal.api.PaymentInitiationApi;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.XmlPaymentProduct;
import de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Link;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.function.Function;

public class IngPsd2PaymentInitiationService implements Psd2PaymentInitiationService {
    private final IngOauth2Service ingOauth2Service;
    private final LinksRewriter linksRewriter;
    private final PaymentInitiationApi paymentInitiationApi;
    private final IngMapper mapper = Mappers.getMapper(IngMapper.class);

    public IngPsd2PaymentInitiationService(PaymentInitiationApi paymentInitiationApi,
                                           IngOauth2Service ingOauth2Service,
                                           LinksRewriter linksRewriter) {
        this.paymentInitiationApi = paymentInitiationApi;
        this.ingOauth2Service = ingOauth2Service;
        this.linksRewriter = linksRewriter;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      PaymentInitiation body) {
        verifySinglePayment(paymentService);
        de.adorsys.xs2a.adapter.service.ing.internal.api.model.PaymentProduct product =
            de.adorsys.xs2a.adapter.service.ing.internal.api.model.PaymentProduct.fromValue(paymentProduct.toString());
        Headers headerParameters = new Headers(headers);
        return paymentInitiationApi.initiatePayment(product,
            headerParameters.getRequestId(),
            headerParameters.getTppRedirectUri(),
            headerParameters.getPsuIpAddress(),
            ingOauth2Service.getClientAuthentication(),
            mapper.map(body))
            .map(mapper::map)
            .map(this::rewriteLinks);
    }

    private PaymentInitiationRequestResponse rewriteLinks(PaymentInitiationRequestResponse body) {
        Map<String, Link> links = mapper.mapToXs2aLinks(body.getLinks());
        body.setLinks(mapper.mapToPsd2Links(linksRewriter.rewrite(links)));
        return body;
    }

    private void verifySinglePayment(PaymentService paymentService) {
        if (paymentService != PaymentService.PAYMENTS) {
            throw new UnsupportedOperationException("Unsupported payment service: " + paymentService);
        }
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiatePayment(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      String body) {
        verifySinglePayment(paymentService);
        XmlPaymentProduct product = XmlPaymentProduct.fromValue(paymentProduct.toString());
        Headers headerParameters = new Headers(headers);
        return paymentInitiationApi.initiatePaymentXml(product,
            headerParameters.getRequestId(),
            headerParameters.getTppRedirectUri(),
            headerParameters.getPsuIpAddress(),
            ingOauth2Service.getClientAuthentication(),
            body)
            .map(mapper::map)
            .map(this::rewriteLinks);
    }

    @Override
    public Response<Object> getPaymentInformation(PaymentService paymentService,
                                                  PaymentProduct paymentProduct,
                                                  String paymentId,
                                                  Map<String, String> queryParameters,
                                                  Map<String, String> headers) {
        verifySinglePayment(paymentService);
        Headers headerParameters = new Headers(headers);
        try {
            de.adorsys.xs2a.adapter.service.ing.internal.api.model.PaymentProduct product =
                de.adorsys.xs2a.adapter.service.ing.internal.api.model.PaymentProduct.fromValue(paymentProduct.toString());
            return paymentInitiationApi.getPaymentDetails(product,
                paymentId,
                headerParameters.getRequestId(),
                headerParameters.getPsuIpAddress(),
                ingOauth2Service.getClientAuthentication())
                .map(mapper::map);
        } catch (IllegalArgumentException e) {
            XmlPaymentProduct product = XmlPaymentProduct.fromValue(paymentProduct.toString());
            return paymentInitiationApi.getPaymentDetailsXml(product,
                paymentId,
                headerParameters.getRequestId(),
                headerParameters.getPsuIpAddress(),
                ingOauth2Service.getClientAuthentication())
                .map(Function.identity());
        }

    }

    @Override
    public Response<Object> getPaymentInitiationStatus(PaymentService paymentService,
                                                       PaymentProduct paymentProduct,
                                                       String paymentId,
                                                       Map<String, String> queryParameters,
                                                       Map<String, String> headers) {
        verifySinglePayment(paymentService);
        Headers headerParameters = new Headers(headers);
        try {
            de.adorsys.xs2a.adapter.service.ing.internal.api.model.PaymentProduct product =
                de.adorsys.xs2a.adapter.service.ing.internal.api.model.PaymentProduct.fromValue(paymentProduct.toString());
            return paymentInitiationApi.getPaymentStatus(product,
                paymentId,
                headerParameters.getRequestId(),
                headerParameters.getPsuIpAddress(),
                ingOauth2Service.getClientAuthentication())
                .map(mapper::map);
        } catch (IllegalArgumentException e) {
            XmlPaymentProduct product = XmlPaymentProduct.fromValue(paymentProduct.toString());
            return paymentInitiationApi.getPaymentStatusXml(product,
                paymentId,
                headerParameters.getRequestId(),
                headerParameters.getPsuIpAddress(),
                ingOauth2Service.getClientAuthentication())
                .map(Function.identity());
        }
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaProcessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation body) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     Map<String, String> queryParameters,
                                                                     Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdateAuthorisationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      String authorisationId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers,
                                                                      UpdateAuthorisation body) {
        throw new UnsupportedOperationException();
    }
}
