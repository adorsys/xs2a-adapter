package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AspspTO;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AspspMapper {

    List<AspspTO> toAspspTOs(Iterable<Aspsp> aspsps);
}
