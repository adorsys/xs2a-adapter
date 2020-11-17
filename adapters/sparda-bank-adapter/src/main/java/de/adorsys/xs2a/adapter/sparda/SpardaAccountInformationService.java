package de.adorsys.xs2a.adapter.sparda;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.sparda.model.SpardaOK200TransactionDetails;
import de.adorsys.xs2a.adapter.sparda.model.SpardaTransactionResponse200Json;
import org.mapstruct.factory.Mappers;

import java.util.Map;

import static java.util.function.Function.identity;

public class SpardaAccountInformationService extends BaseAccountInformationService {
    private static final String AIS_SCOPE = "ais";
    private static final String BEARER_TOKEN_TYPE_PREFIX = "Bearer ";

    private final SpardaJwtService spardaJwtService;
    private final SpardaMapper spardaMapper = Mappers.getMapper(SpardaMapper.class);
    private final ResponseHandlers responseHandlers;

    public SpardaAccountInformationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           LinksRewriter linksRewriter,
                                           SpardaJwtService spardaJwtService,
                                           HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, linksRewriter, logSanitizer);
        this.spardaJwtService = spardaJwtService;
        this.responseHandlers = new ResponseHandlers(logSanitizer);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        if (isOauthPreStep(requestHeaders)) {
            requestHeaders = modifyPsuId(requestHeaders);
        }
        String idpUri = StringUri.appendQueryParam(getIdpUri(), Oauth2Service.Parameters.SCOPE, AIS_SCOPE);

        return createConsent(requestHeaders,
            requestParams,
            body,
            identity(),
            responseHandlers.consentCreationResponseHandler(idpUri, ConsentsResponse201.class));
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        SpardaTransactionResponse200Json.class,
                                        spardaMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {

        return super.getTransactionDetails(accountId,
                                           transactionId,
                                           requestHeaders,
                                           requestParams,
                                           SpardaOK200TransactionDetails.class,
                                           spardaMapper::toOK200TransactionDetails);
    }

    private boolean isOauthPreStep(RequestHeaders requestHeaders) {
        return requestHeaders.get(RequestHeaders.AUTHORIZATION)
                   .map(authHeader -> authHeader.startsWith(BEARER_TOKEN_TYPE_PREFIX))
                   .orElse(false);
    }

    private RequestHeaders modifyPsuId(RequestHeaders requestHeaders) {
        // .orElse(null) should never be the case, as this method is invoked for OAuth pre-step only
        String token = requestHeaders.getAuthorization()
                           .map(this::getBearerToken)
                           .orElse(null);
        Map<String, String> headersMap = requestHeaders.toMap();
        headersMap.put(RequestHeaders.PSU_ID.toLowerCase(), spardaJwtService.getPsuId(token));
        return RequestHeaders.fromMap(headersMap);
    }

    private String getBearerToken(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_TOKEN_TYPE_PREFIX.length());
    }
}
