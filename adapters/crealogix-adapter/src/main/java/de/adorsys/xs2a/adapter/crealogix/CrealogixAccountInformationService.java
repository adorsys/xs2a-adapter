package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixOK200TransactionDetails;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixTransactionResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

import static java.util.function.Function.identity;

public class CrealogixAccountInformationService extends BaseAccountInformationService {

    private final CrealogixMapper crealogixMapper = Mappers.getMapper(CrealogixMapper.class);
    private final CrealogixRequestResponseHandlers requestResponseHandlers;

    public CrealogixAccountInformationService(Aspsp aspsp,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter,
                                              HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, linksRewriter, logSanitizer);
        this.requestResponseHandlers = new CrealogixRequestResponseHandlers(logSanitizer);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.createConsent(requestHeaders,
            requestParams,
            body,
            identity(),
            requestResponseHandlers.crealogixResponseHandler(ConsentsResponse201.class));
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        CrealogixTransactionResponse200Json.class,
                                        crealogixMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getTransactionDetails(accountId,
                                           transactionId,
                                           requestHeaders,
                                           requestParams,
                                           CrealogixOK200TransactionDetails.class,
                                           crealogixMapper::toOK200TransactionDetails);
    }
}
