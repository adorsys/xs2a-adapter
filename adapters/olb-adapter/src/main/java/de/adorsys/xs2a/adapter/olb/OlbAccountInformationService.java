package de.adorsys.xs2a.adapter.olb;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.olb.model.OlbOK200TransactionDetails;
import de.adorsys.xs2a.adapter.olb.model.OlbTransactionResponse200Json;
import org.mapstruct.factory.Mappers;

public class OlbAccountInformationService extends BaseAccountInformationService {

    private final OlbMapper olbMapper = Mappers.getMapper(OlbMapper.class);

    public OlbAccountInformationService(Aspsp aspsp, HttpClient httpClient, LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        OlbTransactionResponse200Json.class,
                                        olbMapper::toTransactionsResponse200Json);
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
                                           OlbOK200TransactionDetails.class,
                                           olbMapper::toOK200TransactionDetails);
    }
}
