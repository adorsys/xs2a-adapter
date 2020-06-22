package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.http.JsonMapper;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ing.internal.api.PaymentInitiationApi;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.XmlPaymentProduct;
import de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import org.mapstruct.factory.Mappers;

import java.util.function.Function;

public class IngPaymentInitiationService implements PaymentInitiationService {
    private final IngOauth2Service ingOauth2Service;
    private final LinksRewriter linksRewriter;
    private final PaymentInitiationApi paymentInitiationApi;
    private final IngMapper mapper = Mappers.getMapper(IngMapper.class);
    private final JsonMapper jsonMapper = new JsonMapper();

    public IngPaymentInitiationService(PaymentInitiationApi paymentInitiationApi,
                                       IngOauth2Service ingOauth2Service,
                                       LinksRewriter linksRewriter) {
        this.paymentInitiationApi = paymentInitiationApi;
        this.ingOauth2Service = ingOauth2Service;
        this.linksRewriter = linksRewriter;
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiateSinglePayment(String paymentProduct,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams,
                                                                              Object body) {

        if (body instanceof String) {
            XmlPaymentProduct product = XmlPaymentProduct.fromValue(paymentProduct);
            return paymentInitiationApi.initiatePaymentXml(product,
                requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                requestHeaders.get(RequestHeaders.TPP_REDIRECT_URI).orElse(null),
                requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                ingOauth2Service.getClientAuthentication(),
                (String) body)
                .map(mapper::map)
                .map(this::rewriteLinks);
        }

        PaymentProduct product = PaymentProduct.fromValue(paymentProduct);
        PaymentInitiationJson jsonBody = jsonMapper.convertValue(body, PaymentInitiationJson.class);
        return paymentInitiationApi.initiatePayment(product,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.TPP_REDIRECT_URI).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            ingOauth2Service.getClientAuthentication(),
            mapper.map(jsonBody))
            .map(mapper::map)
            .map(this::rewriteLinks);
    }

    private PaymentInitationRequestResponse201 rewriteLinks(PaymentInitationRequestResponse201 body) {
        body.setLinks(linksRewriter.rewrite(body.getLinks()));
        return body;
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        PaymentProduct product = PaymentProduct.fromValue(paymentProduct);
        return paymentInitiationApi.getPaymentDetails(product,
            paymentId,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            ingOauth2Service.getClientAuthentication())
            .map(mapper::map);
    }

    @Override
    public Response<String> getSinglePaymentInformationAsString(String paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams) {
        XmlPaymentProduct product = XmlPaymentProduct.fromValue(paymentProduct);
        return paymentInitiationApi.getPaymentDetailsXml(product,
            paymentId,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            ingOauth2Service.getClientAuthentication())
            .map(Function.identity());
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                     String paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                                             String paymentId,
                                                                                             RequestHeaders requestHeaders,
                                                                                             RequestParams requestParams) {
        PaymentProduct product = PaymentProduct.fromValue(paymentProduct);
        return paymentInitiationApi.getPaymentStatus(product,
            paymentId,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            ingOauth2Service.getClientAuthentication())
            .map(mapper::map);
    }

    @Override
    public Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                     String paymentId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        XmlPaymentProduct product = XmlPaymentProduct.fromValue(paymentProduct);
        return paymentInitiationApi.getPaymentStatusXml(product,
            paymentId,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            ingOauth2Service.getClientAuthentication())
            .map(Function.identity());
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(String paymentService,
                                                                      String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams,
                                                                             UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                String paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                            String paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                          String paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }
}
