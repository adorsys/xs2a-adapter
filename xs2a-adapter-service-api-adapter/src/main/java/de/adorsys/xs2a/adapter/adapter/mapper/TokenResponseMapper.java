package de.adorsys.xs2a.adapter.adapter.mapper;

import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.Mapper;

@Mapper
public interface TokenResponseMapper {

    TokenResponse map(OauthToken value);
}
