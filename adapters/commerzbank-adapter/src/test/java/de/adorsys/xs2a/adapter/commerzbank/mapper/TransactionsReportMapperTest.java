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

package de.adorsys.xs2a.adapter.commerzbank.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankTransactionsReport;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionsReportMapperTest {

    public static final String COMMERZBANK_TRANSACTIONS_REPORT_PATH = "ais/commerzbank-transactions-report.json";
    public static final String TRANSACTIONS_RESPONSE_200_JSON_PATH = "ais/transactions-response-200-json.json";

    private final TransactionsReportMapper mapper
        = new TransactionsReportMapperImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void map() throws IOException {
        var commerzbankTransactionsReport = objectMapper.readValue(Resources.getResource(COMMERZBANK_TRANSACTIONS_REPORT_PATH), CommerzbankTransactionsReport.class);
        TransactionsResponse200Json actual = mapper.toTransactionsReport(commerzbankTransactionsReport);

        var expected = objectMapper.readValue(Resources.getResource(TRANSACTIONS_RESPONSE_200_JSON_PATH), TransactionsResponse200Json.class);

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

}
