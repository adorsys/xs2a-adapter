package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AmountTO;
import de.adorsys.xs2a.adapter.service.Amount;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class AmountMapperTest {
    private static final String CURRENCY = "EUR";
    private static final String AMOUNT = "100";

    @Test
    public void toAmountTO() {
        AmountTO amountTO = Mappers.getMapper(AmountMapper.class).toAmountTO(buildAmount());

        assertThat(amountTO).isNotNull();
        assertThat(amountTO.getAmount()).isEqualTo(AMOUNT);
        assertThat(amountTO.getCurrency()).isEqualTo(CURRENCY);
    }

    static Amount buildAmount() {
        Amount amount = new Amount();

        amount.setCurrency(CURRENCY);
        amount.setAmount(AMOUNT);

        return amount;
    }
}
