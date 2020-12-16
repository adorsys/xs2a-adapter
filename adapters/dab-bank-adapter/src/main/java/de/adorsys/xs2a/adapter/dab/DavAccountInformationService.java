package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.dab.model.DabOK200TransactionDetails;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class DavAccountInformationService extends BaseAccountInformationService {

    private final DabMapper mapper = Mappers.getMapper(DabMapper.class);

    public DavAccountInformationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        LinksRewriter linksRewriter,
                                        HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, linksRewriter, logSanitizer);
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
            DabOK200TransactionDetails.class,
            mapper::toOK200TransactionDetails);
    }
}
