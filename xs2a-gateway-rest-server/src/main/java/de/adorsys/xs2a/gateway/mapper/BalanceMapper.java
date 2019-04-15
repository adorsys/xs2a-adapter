package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.BalanceTO;
import de.adorsys.xs2a.gateway.service.account.Balance;
import org.mapstruct.Mapper;

@Mapper(uses = AmountMapper.class)
public interface BalanceMapper {

    BalanceTO toBalanceTO(Balance balance);
}
