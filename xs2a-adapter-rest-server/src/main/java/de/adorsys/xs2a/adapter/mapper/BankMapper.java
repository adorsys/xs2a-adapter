package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.BankTO;
import de.adorsys.xs2a.adapter.service.Bank;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BankMapper {

    List<BankTO> toBankTOList(List<Bank> configRecords);
}
