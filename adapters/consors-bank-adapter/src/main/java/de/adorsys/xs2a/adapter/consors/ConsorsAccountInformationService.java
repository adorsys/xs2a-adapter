package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.consors.model.ConsorsOK200TransactionDetails;
import de.adorsys.xs2a.adapter.consors.model.ConsorsTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

import java.util.Map;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;

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

    @Override
    protected Map<String, String> addPsuIdHeader(Map<String, String> headers) {
        if (!headers.containsKey(PSU_ID) || StringUtils.isNotEmpty(headers.get(PSU_ID))) {
            headers.put(PSU_ID, "");
        }

        return headers;
    }
}
