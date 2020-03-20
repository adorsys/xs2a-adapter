package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.OK200CardAccountDetailsTO;
import de.adorsys.xs2a.adapter.service.model.CardAccountDetailsHolder;
import org.mapstruct.Mapper;

@Mapper
public interface CardAccountDetailsHolderMapper {
    OK200CardAccountDetailsTO map(CardAccountDetailsHolder source);

    CardAccountDetailsHolder map(OK200CardAccountDetailsTO source);
}
