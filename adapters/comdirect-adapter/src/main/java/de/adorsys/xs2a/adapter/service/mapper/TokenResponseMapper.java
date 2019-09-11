package de.adorsys.xs2a.adapter.service.mapper;

import de.adorsys.xs2a.adapter.service.model.OauthToken;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.Mapper;

@Mapper
public interface TokenResponseMapper {

    TokenResponse map(OauthToken value);
}
