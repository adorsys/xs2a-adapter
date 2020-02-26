package de.adorsys.xs2a.adapter.registry.mapper;

import de.adorsys.xs2a.adapter.registry.AspspCsvRecord;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AspspMapper {

    List<Aspsp> toAspsps(List<AspspCsvRecord> aspspCsvRecords);

    @Mapping(source = "aspspName", target = "name")
    @Mapping(target = "scaApproaches", source = "aspspScaApproaches")
    @Mapping(target = "paginationId", ignore = true)
    Aspsp toAspsp(AspspCsvRecord aspspCsvRecord);

    @Mapping(target = "aspspScaApproaches", source = "scaApproaches")
    @Mapping(source = "name", target = "aspspName")
    AspspCsvRecord toAspspCsvRecord(Aspsp aspsp);
}
