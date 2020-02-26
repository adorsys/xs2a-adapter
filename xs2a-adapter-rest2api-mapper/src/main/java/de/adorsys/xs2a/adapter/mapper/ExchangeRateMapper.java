package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ReportExchangeRateTO;
import de.adorsys.xs2a.adapter.service.model.ExchangeRate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ExchangeRateMapper {

    @Mapping(source = "rate", target = "exchangeRate")
    @Mapping(source = "rateDate", target = "quotationDate")
    @Mapping(source = "rateContract", target = "contractIdentification")
    ReportExchangeRateTO toExchangeRateTO(ExchangeRate exchangeRate);

    @InheritInverseConfiguration
    ExchangeRate toExchangeRate(ReportExchangeRateTO to);
}
