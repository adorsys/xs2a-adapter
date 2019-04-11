package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.ConsentStatusResponse200;
import de.adorsys.xs2a.gateway.service.consent.ConsentStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ConsentStatusResponseMapper {
    ConsentStatusResponse200 toConsentStatusResponse200(ConsentStatusResponse consentStatusResponse);
}
