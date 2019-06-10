package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.BankTO;
import de.adorsys.xs2a.gateway.service.Bank;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BankMapper {

    List<BankTO> toBankTOList(List<Bank> bank);
}
