package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AmountTO;
import de.adorsys.xs2a.gateway.service.Amount;
import org.mapstruct.Mapper;

import java.util.Currency;

@Mapper
public interface AmountMapper {

    AmountTO toAmountTO(Amount amount);

    default String toCurrencyString(Currency currency) {
        if (currency == null) {
            return null;
        }

        return currency.getCurrencyCode();
    }
}
