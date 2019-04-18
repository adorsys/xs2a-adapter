package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.ExchangeRateTO;
import de.adorsys.xs2a.gateway.service.account.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper
public interface ExchangeRateMapper {

    ExchangeRateTO toExchangeRateTO(ExchangeRate exchangeRate);
}
