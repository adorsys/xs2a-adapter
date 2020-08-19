package de.adorsys.xs2a.adapter.comdirect;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.comdirect.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectBalanceReport;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class ComdirectAccountInformationService extends BaseAccountInformationService {

    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);

    public ComdirectAccountInformationService(Aspsp aspsp,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getBalances(accountId, requestHeaders, requestParams, ComdirectBalanceReport.class,
            balanceReportMapper::toBalanceReport);
    }
}
