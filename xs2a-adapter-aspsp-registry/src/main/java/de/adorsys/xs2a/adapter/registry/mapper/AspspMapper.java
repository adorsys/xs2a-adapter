package de.adorsys.xs2a.adapter.registry.mapper;

import de.adorsys.xs2a.adapter.registry.AspspCsvRecord;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface AspspMapper {

    List<Aspsp> toAspsps(List<AspspCsvRecord> aspspCsvRecords);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "aspspName", target = "name")
    })
    Aspsp toAspsp(AspspCsvRecord aspspCsvRecord);
}
