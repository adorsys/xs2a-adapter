package de.adorsys.xs2a.adapter.commerzbank.service.mapper;

import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.Mapper;

@Mapper
public interface TokenResponseMapper {
    TokenResponse map(com.google.api.client.auth.oauth2.TokenResponse value);
}
