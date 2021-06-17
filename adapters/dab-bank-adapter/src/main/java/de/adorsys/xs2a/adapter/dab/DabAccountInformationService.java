package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.dab.model.DabOK200TransactionDetails;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class DabAccountInformationService extends BaseAccountInformationService {

    private final DabMapper mapper = Mappers.getMapper(DabMapper.class);

    public DabAccountInformationService(Aspsp aspsp,
                                        HttpClientFactory httpClientFactory,
                                        LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
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
