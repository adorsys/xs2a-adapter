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
        @Mapping(source = "aspspName", target = "name"),
        @Mapping(target = "paginationId", ignore = true)
    })
    Aspsp toAspsp(AspspCsvRecord aspspCsvRecord);

    @Mapping(source = "name", target = "aspspName")
    AspspCsvRecord toAspspCsvRecord(Aspsp aspsp);
}
