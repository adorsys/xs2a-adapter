package de.adorsys.xs2a.adapter.remote;

import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.mapper.EmbeddedPreAuthorisationMapper;
import de.adorsys.xs2a.adapter.mapper.Oauth2Mapper;
import de.adorsys.xs2a.adapter.remote.client.EmbeddedPreAuthorisationClient;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.mapstruct.factory.Mappers;

public class RemoteEmbeddedPreAuthorisationService implements EmbeddedPreAuthorisationService {

    private final EmbeddedPreAuthorisationClient client;
    private final Oauth2Mapper oauth2Mapper = Mappers.getMapper(Oauth2Mapper.class);
    private final EmbeddedPreAuthorisationMapper embeddedPreAuthorisationMapper = Mappers.getMapper(EmbeddedPreAuthorisationMapper.class);

    public RemoteEmbeddedPreAuthorisationService(EmbeddedPreAuthorisationClient client) {
        this.client = client;
    }

    @Override
    public TokenResponse getToken(EmbeddedPreAuthorisationRequest request, RequestHeaders requestHeaders) {
        TokenResponseTO responseTO
            = client.getToken(requestHeaders.toMap(), embeddedPreAuthorisationMapper.toEmbeddedPreAuthorisationRequestTO(request));
        return oauth2Mapper.toTokenResponse(responseTO);
    }
}
