package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.BalanceTO;
import de.adorsys.xs2a.adapter.service.account.Balance;
import org.mapstruct.Mapper;

@Mapper(uses = AmountMapper.class)
public interface BalanceMapper {

    BalanceTO toBalanceTO(Balance balance);
}
