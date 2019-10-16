package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AspspTO;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AspspMapper {

    AspspTO toAspspTO(Aspsp aspsp);

    Aspsp toAspsp(AspspTO to);

    List<AspspTO> toAspspTOs(Iterable<Aspsp> aspsps);

    List<Aspsp> toAspsps(Iterable<AspspTO> aspsps);
}
