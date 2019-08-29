package de.adorsys.xs2a.adapter.rest.mapper;

import de.adorsys.xs2a.adapter.rest.psd2.model.TokenResponseTO;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Oauth2Mapper {
    TokenResponseTO map(TokenResponse token);
}
