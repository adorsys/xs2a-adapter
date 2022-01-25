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

package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.commerzbank.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.commerzbank.mapper.OK200TransactionDetailsMapper;
import de.adorsys.xs2a.adapter.commerzbank.mapper.TransactionsReportMapper;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankBalanceReport;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankOK200TransactionDetails;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankTransactionsReport;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

public class CommerzbankAccountInformationService extends BaseAccountInformationService {

    private TransactionsReportMapper transactionsReportMapper = Mappers.getMapper(TransactionsReportMapper.class);
    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);
    private final OK200TransactionDetailsMapper transactionDetailsMapper = Mappers.getMapper(OK200TransactionDetailsMapper.class);

    public CommerzbankAccountInformationService(Aspsp aspsp,
                                                HttpClientFactory httpClientFactory,
                                                LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return getTransactionList(accountId, requestHeaders, requestParams, CommerzbankTransactionsReport.class,
            transactionsReportMapper::toTransactionsReport);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getBalances(accountId, requestHeaders, requestParams, CommerzbankBalanceReport.class,
            balanceReportMapper::toBalanceReport);
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
                                           CommerzbankOK200TransactionDetails.class,
                                           transactionDetailsMapper::toOK200TransactionDetails);
    }
}
