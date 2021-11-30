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
