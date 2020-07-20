package de.adorsys.xs2a.adapter.fiducia.mapper;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.ReportExchangeRate;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaExchangeRate;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaOK200TransactionDetails;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaTransactionDetails;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class FiduciaMapperTest {

    private final FiduciaMapper mapper = Mappers.getMapper(FiduciaMapper.class);

    @Test
    void toListOfStringsReturnsNullForNullInput() {
        assertNull(mapper.toListOfStrings(null));
    }

    @Test
    void toListOfStringsCreatesListWithOneItem() {
        assertThat(mapper.toListOfStrings("asdf")).containsExactly("asdf");
    }

    @Test
    void exchangeRateMapping() {
        FiduciaOK200TransactionDetails value = new FiduciaOK200TransactionDetails();
        FiduciaTransactionDetails transactionDetails = new FiduciaTransactionDetails();
        FiduciaExchangeRate er = new FiduciaExchangeRate();
        er.setCurrencyFrom("EUR");
        er.setCurrencyTo("USD");
        er.setRateContract("rateContract");
        er.setRateFrom("2");
        er.setRateTo("1");
        er.setRateDate(LocalDate.of(2020, 2, 22));
        transactionDetails.setExchangeRate(Collections.singletonList(er));
        value.setTransactionsDetails(transactionDetails);
        OK200TransactionDetails result = mapper.toOK200TransactionDetails(value);

        ReportExchangeRate mapped = result.getTransactionsDetails().getCurrencyExchange().get(0);
        assertThat(mapped.getContractIdentification()).isEqualTo("rateContract");
        assertThat(mapped.getExchangeRate()).isEqualTo("0.5");
        assertThat(mapped.getQuotationDate()).isEqualTo(LocalDate.of(2020, 2, 22));
        assertThat(mapped.getSourceCurrency()).isEqualTo("EUR");
        assertThat(mapped.getTargetCurrency()).isEqualTo("USD");
    }
}
