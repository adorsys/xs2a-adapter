package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AmountTO;
import de.adorsys.xs2a.adapter.service.Amount;
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
