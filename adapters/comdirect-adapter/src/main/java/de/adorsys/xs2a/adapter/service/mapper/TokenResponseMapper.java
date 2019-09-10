package de.adorsys.xs2a.adapter.service.mapper;

import de.adorsys.xs2a.adapter.service.model.OauthToken;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TokenResponseMapper {

    @Mapping(source = "access_token", target = "accessToken")
    @Mapping(source = "token_type", target = "tokenType")
    @Mapping(source = "expires_in", target = "expiresInSeconds")
    @Mapping(source = "refresh_token", target = "refreshToken")
    TokenResponse map(OauthToken value);
}
