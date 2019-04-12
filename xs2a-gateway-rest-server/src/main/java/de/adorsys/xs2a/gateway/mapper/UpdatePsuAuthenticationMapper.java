package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.UpdatePsuAuthenticationTO;
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;
import org.mapstruct.Mapper;

@Mapper
public interface UpdatePsuAuthenticationMapper {
    UpdatePsuAuthentication toUpdatePsuAuthentication(UpdatePsuAuthenticationTO updatePsuAuthenticationTO);
}
