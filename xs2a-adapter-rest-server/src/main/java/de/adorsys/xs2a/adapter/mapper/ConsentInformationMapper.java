package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ConsentInformationResponse200JsonTO;
import de.adorsys.xs2a.adapter.service.ais.ConsentInformation;
import org.mapstruct.Mapper;

@Mapper
public interface ConsentInformationMapper {
    ConsentInformationResponse200JsonTO toConsentInformationResponse200Json(ConsentInformation consentInformation);

    ConsentInformation toConsentInformation(ConsentInformationResponse200JsonTO to);
}
