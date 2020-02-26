package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.model.DkbSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeDataDkbMapper.class)
public interface SelectPsuAuthenticationMethodResponseDkbMapper {

    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(DkbSelectPsuAuthenticationMethodResponse response);
}
