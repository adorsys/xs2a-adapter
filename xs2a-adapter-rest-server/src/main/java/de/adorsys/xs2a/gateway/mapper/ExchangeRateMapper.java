package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ReportExchangeRateTO;
import de.adorsys.xs2a.gateway.service.account.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ExchangeRateMapper {

    @Mapping(source = "rate", target = "exchangeRate")
    @Mapping(source = "rateDate", target = "quotationDate")
    @Mapping(source = "rateContract", target = "contractIdentification")
    ReportExchangeRateTO toExchangeRateTO(ExchangeRate exchangeRate);
}
