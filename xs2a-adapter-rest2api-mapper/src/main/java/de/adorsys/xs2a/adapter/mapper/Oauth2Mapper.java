package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.mapstruct.Mapper;

@Mapper
public interface Oauth2Mapper {
    TokenResponseTO map(TokenResponse token);

    TokenResponse toTokenResponse(TokenResponseTO tokenResponseTO);
}
