package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.model.DkbUpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeDataDkbMapper.class)
public interface UpdatePsuAuthenticationResponseDkbMapper {

    UpdatePsuAuthenticationResponse toUpdatePsuAuthenticationResponse(DkbUpdatePsuAuthenticationResponse response);
}
