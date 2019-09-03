package de.adorsys.xs2a.adapter.adorsys.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.ConsentCreationResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.SelectPsuAuthenticationMethodResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.StartScaProcessResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.UpdatePsuAuthenticationResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegConsentCreationResponse;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegStartScaProcessResponse;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegUpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.model.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse;
import org.mapstruct.factory.Mappers;

public class AdorsysIntegAccountInformationService extends BaseAccountInformationService {
    private final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ConsentCreationResponseMapper creationResponseMapper = Mappers.getMapper(ConsentCreationResponseMapper.class);
    private final UpdatePsuAuthenticationResponseMapper updatePsuAuthenticationResponseMapper = Mappers.getMapper(UpdatePsuAuthenticationResponseMapper.class);
    private final SelectPsuAuthenticationMethodResponseMapper selectPsuAuthenticationMethodResponseMapper = Mappers.getMapper(SelectPsuAuthenticationMethodResponseMapper.class);

    public AdorsysIntegAccountInformationService(String baseUri) {
        super(baseUri);
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body) {
        return createConsent(requestHeaders, body, AdorsysIntegConsentCreationResponse.class, creationResponseMapper::toConsentCreationResponse);
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        return startConsentAuthorisation(consentId, requestHeaders, AdorsysIntegStartScaProcessResponse.class, startScaProcessResponseMapper::toStartScaProcessResponse);
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return startConsentAuthorisation(consentId, requestHeaders, updatePsuAuthentication, AdorsysIntegStartScaProcessResponse.class, startScaProcessResponseMapper::toStartScaProcessResponse);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return updateConsentsPsuData(consentId, authorisationId, requestHeaders, updatePsuAuthentication, AdorsysIntegUpdatePsuAuthenticationResponse.class, updatePsuAuthenticationResponseMapper::toUpdatePsuAuthenticationResponse);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updateConsentsPsuData(consentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod, AdorsysIntegSelectPsuAuthenticationMethodResponse.class, selectPsuAuthenticationMethodResponseMapper::toSelectPsuAuthenticationMethodResponse);
    }


}
