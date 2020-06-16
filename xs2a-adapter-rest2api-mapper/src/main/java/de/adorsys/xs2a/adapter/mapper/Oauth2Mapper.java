package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.Mapper;

@Mapper
public interface Oauth2Mapper {
    TokenResponseTO map(TokenResponse token);

    TokenResponse toTokenResponse(TokenResponseTO tokenResponseTO);
}
