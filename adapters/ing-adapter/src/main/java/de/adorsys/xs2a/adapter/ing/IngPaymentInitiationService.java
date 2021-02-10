package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.ing.model.IngPaymentProduct;
import de.adorsys.xs2a.adapter.ing.model.IngXmlPaymentProduct;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;
import static de.adorsys.xs2a.adapter.api.validation.ValidationError.Code.NOT_SUPPORTED;

public class IngPaymentInitiationService implements PaymentInitiationService {
    private final IngOauth2Service ingOauth2Service;
    private final LinksRewriter linksRewriter;
    private final IngPaymentInitiationApi paymentInitiationApi;
    private final IngMapper mapper = Mappers.getMapper(IngMapper.class);
    private final JsonMapper jsonMapper = new JacksonObjectMapper();

    public IngPaymentInitiationService(IngPaymentInitiationApi paymentInitiationApi,
                                       IngOauth2Service ingOauth2Service,
                                       LinksRewriter linksRewriter) {
        this.paymentInitiationApi = paymentInitiationApi;
        this.ingOauth2Service = ingOauth2Service;
        this.linksRewriter = linksRewriter;
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        requireValid(validateInitiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body));

        switch (paymentService) {
            case PAYMENTS:
                if (body instanceof String) {
                    IngXmlPaymentProduct product = IngXmlPaymentProduct.of(paymentProduct);
                    return paymentInitiationApi.initiatePaymentXml(product,
                        requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                        requestHeaders.get(RequestHeaders.TPP_REDIRECT_URI).orElse(null),
                        requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                        Collections.singletonList(ingOauth2Service.getClientAuthentication()),
                        (String) body)
                        .map(mapper::map)
                        .map(this::rewriteLinks);
                }

                IngPaymentProduct product = IngPaymentProduct.of(paymentProduct);
                PaymentInitiationJson jsonBody = jsonMapper.convertValue(body, PaymentInitiationJson.class);
                return paymentInitiationApi.initiatePayment(product,
                    requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                    requestHeaders.get(RequestHeaders.TPP_REDIRECT_URI).orElse(null),
                    requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                    Collections.singletonList(ingOauth2Service.getClientAuthentication()),
                    mapper.map(jsonBody))
                    .map(mapper::map)
                    .map(this::rewriteLinks);

            case PERIODIC_PAYMENTS:
                if (body instanceof PeriodicPaymentInitiationMultipartBody) {
                    return paymentInitiationApi.initiatePeriodicPaymentXml(IngXmlPaymentProduct.of(paymentProduct),
                        requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                        requestHeaders.get(RequestHeaders.TPP_REDIRECT_URI).orElse(null),
                        requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                        Collections.singletonList(ingOauth2Service.getClientAuthentication()),
                        mapper.map((PeriodicPaymentInitiationMultipartBody) body))
                        .map(mapper::map)
                        .map(this::rewriteLinks);
                }

                return paymentInitiationApi.initiatePeriodicPayment(IngPaymentProduct.of(paymentProduct),
                    requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                    requestHeaders.get(RequestHeaders.TPP_REDIRECT_URI).orElse(null),
                    requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                    Collections.singletonList(ingOauth2Service.getClientAuthentication()),
                    mapper.map(jsonMapper.convertValue(body, PeriodicPaymentInitiationJson.class)))
                    .map(mapper::map)
                    .map(this::rewriteLinks);


            default:
                throw new UnsupportedOperationException(paymentService.toString());
        }
    }

    @Override
    public List<ValidationError> validateInitiatePayment(PaymentService paymentService,
                                                         PaymentProduct paymentProduct,
                                                         RequestHeaders requestHeaders,
                                                         RequestParams requestParams,
                                                         Object body) {
        if (paymentService == PaymentService.PERIODIC_PAYMENTS
            && !(body instanceof PeriodicPaymentInitiationMultipartBody)) {

            FrequencyCode frequency = jsonMapper.convertValue(body, PeriodicPaymentInitiationJson.class).getFrequency();
            try {
                mapper.map(frequency);
            } catch (IllegalArgumentException e) {
                return Collections.singletonList(
                    new ValidationError(NOT_SUPPORTED, "frequency", "\"" + frequency + "\" value is not supported"));
            }
        }
        return Collections.emptyList();
    }

    private PaymentInitationRequestResponse201 rewriteLinks(PaymentInitationRequestResponse201 body) {
        body.setLinks(linksRewriter.rewrite(body.getLinks()));
        return body;
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        IngPaymentProduct product = IngPaymentProduct.of(paymentProduct);
        return paymentInitiationApi.getPaymentDetails(product,
            paymentId,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            Collections.singletonList(ingOauth2Service.getClientAuthentication()))
                   .map(mapper::map);
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {
        IngPaymentProduct product = IngPaymentProduct.of(paymentProduct);
        return paymentInitiationApi.getPeriodicPaymentDetails(product,
            paymentId,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            Collections.singletonList(ingOauth2Service.getClientAuthentication()))
            .map(mapper::map);
    }

    @Override
    public Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                                                 String paymentId,
                                                                                                 RequestHeaders requestHeaders,
                                                                                                 RequestParams requestParams) {
        IngXmlPaymentProduct product = IngXmlPaymentProduct.of(paymentProduct);
        return paymentInitiationApi.getPeriodicPaymentDetailsXml(product,
            paymentId,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
            Collections.singletonList(ingOauth2Service.getClientAuthentication()))
            .map(mapper::map);
    }

    @Override
    public Response<String> getPaymentInformationAsString(PaymentService paymentService,
                                                          PaymentProduct paymentProduct,
                                                          String paymentId,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams) {
        switch (paymentService) {
            case PAYMENTS:
                if (isXml(paymentProduct)) {
                    IngXmlPaymentProduct product = IngXmlPaymentProduct.of(paymentProduct);
                    return paymentInitiationApi.getPaymentDetailsXml(product,
                        paymentId,
                        requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                        requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                        Collections.singletonList(ingOauth2Service.getClientAuthentication()))
                        .map(Function.identity());
                }
                return getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams)
                    .map(jsonMapper::writeValueAsString);

            case PERIODIC_PAYMENTS:
                if (isXml(paymentProduct)) {
                    return getPeriodicPain001PaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams)
                        .map(jsonMapper::writeValueAsString);
                }
                return getPeriodicPaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams)
                    .map(jsonMapper::writeValueAsString);

            default:
                throw new UnsupportedOperationException(paymentService.toString());
        }
    }

    protected boolean isXml(PaymentProduct paymentProduct) {
        return paymentProduct.toString().startsWith("pain");
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(PaymentService paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        IngPaymentProduct product = IngPaymentProduct.of(paymentProduct);
        switch (paymentService) {
            case PAYMENTS:
                return paymentInitiationApi.getPaymentStatus(product,
                    paymentId,
                    requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                    requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                    Collections.singletonList(ingOauth2Service.getClientAuthentication()))
                    .map(mapper::map);

            case PERIODIC_PAYMENTS:
                return paymentInitiationApi.getPeriodicPaymentStatus(product,
                    paymentId,
                    requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                    requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                    Collections.singletonList(ingOauth2Service.getClientAuthentication()))
                    .map(mapper::map);

            default:
                throw new UnsupportedOperationException(paymentService.toString());
        }

    }

    @Override
    public Response<String> getPaymentInitiationStatusAsString(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        switch (paymentService) {
            case PAYMENTS:
                if (isXml(paymentProduct)) {
                    IngXmlPaymentProduct product = IngXmlPaymentProduct.of(paymentProduct);
                    return paymentInitiationApi.getPaymentStatusXml(product,
                        paymentId,
                        requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                        requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                        Collections.singletonList(ingOauth2Service.getClientAuthentication()))
                        .map(Function.identity());
                }
                return getPaymentInitiationStatus(paymentService, paymentProduct, paymentId, requestHeaders, requestParams)
                    .map(jsonMapper::writeValueAsString);

            case PERIODIC_PAYMENTS:
                if (isXml(paymentProduct)) {
                    return paymentInitiationApi.getPeriodicPaymentStatusXml(IngXmlPaymentProduct.of(paymentProduct),
                        paymentId,
                        requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
                        requestHeaders.get(RequestHeaders.PSU_IP_ADDRESS).orElse(null),
                        Collections.singletonList(ingOauth2Service.getClientAuthentication()))
                        .map(Function.identity());
                }
                return getPaymentInitiationStatus(paymentService, paymentProduct, paymentId, requestHeaders, requestParams)
                    .map(jsonMapper::writeValueAsString);

            default:
                throw new UnsupportedOperationException(paymentService.toString());
        }
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                            PaymentProduct paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }
}
