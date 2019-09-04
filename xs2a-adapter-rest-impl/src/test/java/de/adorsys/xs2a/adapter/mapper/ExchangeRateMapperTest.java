package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ReportExchangeRateTO;
import de.adorsys.xs2a.adapter.service.model.ExchangeRate;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRateMapperTest {
    private static final String SOURCE_CURRENCY = "EUR";
    private static final String RATE = "1.1";
    private static final String UNIT_CURRENCY = "USD";
    private static final String TARGET_CURRENCY = "UAH";
    private static final LocalDate RATE_DATE = LocalDate.now();
    private static final String RATE_CONTRACT = "rateContract";

    @Test
    public void toExchangeRateTO() {
        ReportExchangeRateTO exchangeRateTO = Mappers.getMapper(ExchangeRateMapper.class).toExchangeRateTO(buildExchangeRate());

        assertThat(exchangeRateTO).isNotNull();
        assertThat(exchangeRateTO.getSourceCurrency()).isEqualTo(SOURCE_CURRENCY);
        assertThat(exchangeRateTO.getExchangeRate()).isEqualTo(RATE);
        assertThat(exchangeRateTO.getUnitCurrency()).isEqualTo(UNIT_CURRENCY);
        assertThat(exchangeRateTO.getTargetCurrency()).isEqualTo(TARGET_CURRENCY);
        assertThat(exchangeRateTO.getQuotationDate()).isEqualTo(RATE_DATE);
        assertThat(exchangeRateTO.getContractIdentification()).isEqualTo(RATE_CONTRACT);
    }

    static ExchangeRate buildExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setSourceCurrency(SOURCE_CURRENCY);
        exchangeRate.setRate(RATE);
        exchangeRate.setUnitCurrency(UNIT_CURRENCY);
        exchangeRate.setTargetCurrency(TARGET_CURRENCY);
        exchangeRate.setRateDate(RATE_DATE);
        exchangeRate.setRateContract(RATE_CONTRACT);

        return exchangeRate;
    }
}
