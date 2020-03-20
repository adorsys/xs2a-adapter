package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.CardAccountListTO;
import de.adorsys.xs2a.adapter.service.model.CardAccountList;
import org.mapstruct.Mapper;

@Mapper
public interface CardAccountListMapper {
    CardAccountListTO map(CardAccountList source);

    CardAccountList map(CardAccountListTO source);
}
