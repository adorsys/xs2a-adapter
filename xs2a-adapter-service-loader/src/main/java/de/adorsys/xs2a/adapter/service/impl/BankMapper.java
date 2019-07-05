package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BankMapper {
    List<Bank> toBanks(List<AspspAdapterConfigRecord> aspsps);

    @Mapping(target = "name", source = "aspspName")
    Bank toBankTO(AspspAdapterConfigRecord record);
}
