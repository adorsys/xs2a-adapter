package de.adorsys.xs2a.adapter.dkb.mapper;

import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.dkb.model.DkbUpdatePsuAuthenticationResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeDataDkbMapper.class)
public interface UpdatePsuAuthenticationResponseDkbMapper {

    UpdatePsuAuthenticationResponse toUpdatePsuAuthenticationResponse(DkbUpdatePsuAuthenticationResponse response);
}
