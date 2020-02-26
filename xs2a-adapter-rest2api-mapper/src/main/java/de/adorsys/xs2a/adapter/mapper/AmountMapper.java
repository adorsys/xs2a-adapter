package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AmountTO;
import de.adorsys.xs2a.adapter.service.model.Amount;
import org.mapstruct.Mapper;

import java.util.Currency;

@Mapper
public interface AmountMapper {

    AmountTO toAmountTO(Amount amount);

    Amount toAmount(AmountTO to);

    default String toCurrencyString(Currency currency) {
        if (currency == null) {
            return null;
        }

        return currency.getCurrencyCode();
    }

    default Currency toCurrency(String currencyCode) {
        if (currencyCode != null && currencyCode.trim().isEmpty()) {
            return Currency.getInstance(currencyCode);
        }
        return null;
    }
}
