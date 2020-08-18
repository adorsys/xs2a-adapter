package de.adorsys.xs2a.adapter.dkb.mapper;

import de.adorsys.xs2a.adapter.api.model.SelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.dkb.model.DkbSelectPsuAuthenticationMethodResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeDataDkbMapper.class)
public interface SelectPsuAuthenticationMethodResponseDkbMapper {

    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(DkbSelectPsuAuthenticationMethodResponse response);
}
