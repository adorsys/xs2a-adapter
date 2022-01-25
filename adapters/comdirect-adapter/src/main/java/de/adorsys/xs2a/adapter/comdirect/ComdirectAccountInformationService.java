/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.comdirect;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.comdirect.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.comdirect.mapper.OK200TransactionDetailsMapper;
import de.adorsys.xs2a.adapter.comdirect.mapper.TransactionsResponseMapper;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectBalanceReport;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectOK200TransactionDetails;
import de.adorsys.xs2a.adapter.comdirect.model.ComdirectTransactionResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class ComdirectAccountInformationService extends BaseAccountInformationService {

    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);
    private TransactionsResponseMapper transactionsResponseMapper = Mappers.getMapper(TransactionsResponseMapper.class);
    private OK200TransactionDetailsMapper transactionDetailsMapper = Mappers.getMapper(OK200TransactionDetailsMapper.class);

    public ComdirectAccountInformationService(Aspsp aspsp,
                                              HttpClientFactory httpClientFactory,
                                              LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getBalances(accountId, requestHeaders, requestParams, ComdirectBalanceReport.class,
            balanceReportMapper::toBalanceReport);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        ComdirectTransactionResponse200Json.class,
                                        transactionsResponseMapper::toTransactionsResponse200Json);
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
                                           ComdirectOK200TransactionDetails.class,
                                           transactionDetailsMapper::toOK200TransactionDetails);
    }
}
