package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.ConsentInformationResponse200Json;
import de.adorsys.xs2a.gateway.service.consent.ConsentInformation;
import org.mapstruct.Mapper;

@Mapper
public interface ConsentInformationMapper {
    ConsentInformationResponse200Json toConsentInformationResponse200Json(ConsentInformation consentInformation);
}
