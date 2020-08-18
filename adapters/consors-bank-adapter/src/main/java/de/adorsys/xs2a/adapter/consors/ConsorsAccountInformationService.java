package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.consors.model.ConsorsOK200TransactionDetails;
import de.adorsys.xs2a.adapter.consors.model.ConsorsTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;

public class ConsorsAccountInformationService extends BaseAccountInformationService {

    private final ConsorsMapper mapper = Mappers.getMapper(ConsorsMapper.class);
    public ConsorsAccountInformationService(Aspsp aspsp,
                                            HttpClient httpClient,
                                            PsuIdHeaderInterceptor interceptor,
                                            LinksRewriter linksRewriter) {
        super(aspsp, httpClient, interceptor, linksRewriter);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return getTransactionList(accountId,
            requestHeaders,
            requestParams,
            ConsorsTransactionsResponse200Json.class,
            mapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return getTransactionDetails(accountId,
            transactionId,
            requestHeaders,
            requestParams,
            ConsorsOK200TransactionDetails.class,
            mapper::toOK200TransactionDetails);
    }
}
