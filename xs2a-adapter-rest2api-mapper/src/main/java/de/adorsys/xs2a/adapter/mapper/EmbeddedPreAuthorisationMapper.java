package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.rest.api.model.EmbeddedPreAuthorisationRequestTO;
import org.mapstruct.Mapper;

@Mapper
public interface EmbeddedPreAuthorisationMapper {
    EmbeddedPreAuthorisationRequestTO toEmbeddedPreAuthorisationRequestTO(EmbeddedPreAuthorisationRequest value);
    EmbeddedPreAuthorisationRequest toEmbeddedPreAuthorisationRequest(EmbeddedPreAuthorisationRequestTO value);
}
