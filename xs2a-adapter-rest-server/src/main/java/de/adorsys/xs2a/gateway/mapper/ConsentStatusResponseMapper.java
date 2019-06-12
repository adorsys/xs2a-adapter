package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ConsentStatusResponse200TO;
import de.adorsys.xs2a.gateway.service.ais.ConsentStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ConsentStatusResponseMapper {
    ConsentStatusResponse200TO toConsentStatusResponse200(ConsentStatusResponse consentStatusResponse);
}
