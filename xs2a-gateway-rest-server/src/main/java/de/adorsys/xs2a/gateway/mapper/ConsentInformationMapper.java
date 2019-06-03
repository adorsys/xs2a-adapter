package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ConsentInformationResponse200JsonTO;
import de.adorsys.xs2a.gateway.service.ais.ConsentInformation;
import org.mapstruct.Mapper;

@Mapper
public interface ConsentInformationMapper {
    ConsentInformationResponse200JsonTO toConsentInformationResponse200Json(ConsentInformation consentInformation);
}
