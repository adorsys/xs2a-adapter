package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adapter.StandardPaymentProduct;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationRequestResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.paymentInitiationResponseHandler;
import static java.util.function.Function.identity;

public class SpardaPaymentInitiationService extends BasePaymentInitiationService {
    private static final String PIS_SCOPE = "pis";

    private final SpardaJwtService spardaJwtService;

    public SpardaPaymentInitiationService(Aspsp aspsp,
                                          HttpClient httpClient,
                                          LinksRewriter linksRewriter,
                                          SpardaJwtService spardaJwtService) {
        super(aspsp, httpClient, linksRewriter);
        this.spardaJwtService = spardaJwtService;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams,
                                                                            Object body) {
        if (isOauthPreStep(requestHeaders)) {
            requestHeaders = modifyPsuId(requestHeaders);
        }
        String idpUri = StringUri.appendQueryParam(getIdpUri(), Oauth2Service.Parameters.SCOPE, PIS_SCOPE);

        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct),
            body,
            requestHeaders,
            requestParams,
            identity(),
            paymentInitiationResponseHandler(idpUri, PaymentInitiationRequestResponse.class));
    }

    private boolean isOauthPreStep(RequestHeaders requestHeaders) {
        return requestHeaders.get(RequestHeaders.AUTHORIZATION)
                   .map(StringUtils::isNotBlank)
                   .orElse(false);
    }

    private RequestHeaders modifyPsuId(RequestHeaders requestHeaders) {
        Map<String, String> headersMap = requestHeaders.toMap();
        String token = headersMap.get(RequestHeaders.AUTHORIZATION);
        headersMap.put(RequestHeaders.PSU_ID, spardaJwtService.getPsuId(token));
        return RequestHeaders.fromMap(headersMap);
    }
}
