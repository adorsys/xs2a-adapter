package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.BankTO;
import de.adorsys.xs2a.adapter.service.impl.AspspAdapterConfigRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BankMapper {

    @Mapping(target = "name", source = "aspspName")
    BankTO toBankTO(AspspAdapterConfigRecord record);

    List<BankTO> toBankTOList(List<AspspAdapterConfigRecord> configRecords);
}
