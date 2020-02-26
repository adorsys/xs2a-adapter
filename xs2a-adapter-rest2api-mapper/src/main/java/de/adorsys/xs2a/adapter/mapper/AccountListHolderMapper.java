package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AccountListTO;
import de.adorsys.xs2a.adapter.service.model.AccountListHolder;
import org.mapstruct.Mapper;

@Mapper(uses = {AccountDetailsMapper.class})
public interface AccountListHolderMapper {

    AccountListTO toAccountListTO(AccountListHolder accountListHolder);

    AccountListHolder toAccountListHolder(AccountListTO to);
}
