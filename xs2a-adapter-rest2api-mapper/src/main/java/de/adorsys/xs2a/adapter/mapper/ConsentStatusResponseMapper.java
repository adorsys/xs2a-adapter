package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ConsentStatusResponse200TO;
import de.adorsys.xs2a.adapter.service.model.ConsentStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ConsentStatusResponseMapper {
    ConsentStatusResponse200TO toConsentStatusResponse200(ConsentStatusResponse consentStatusResponse);

    ConsentStatusResponse toConsentStatusResponse(ConsentStatusResponse200TO to);
}
