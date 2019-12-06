package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.Link;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;

import java.util.*;
import java.util.stream.Collectors;

public class UpdatePsuAuthenticationResponseUnicreditMapper {

    private static ScaStatusMapper scaStatusMapper = new ScaStatusMapper();

    public UpdatePsuAuthenticationResponse modifyResponse(UnicreditStartScaProcessResponse response) {
        return toUpdatePsuAuthenticationResponse(response);
    }

    public UpdatePsuAuthenticationResponse toUpdatePsuAuthenticationResponse(UnicreditStartScaProcessResponse response) {

        return Optional.ofNullable(response).map(element -> {
            UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = new UpdatePsuAuthenticationResponse();

            updatePsuAuthenticationResponse.setChallengeData(element.getChallengeData());
            updatePsuAuthenticationResponse.setChosenScaMethod(element.getChosenScaMethod());
            updatePsuAuthenticationResponse.setLinks(toHrefType(element.getLinks()));
            updatePsuAuthenticationResponse.setPsuMessage(element.getPsuMessage());
            updatePsuAuthenticationResponse.setScaMethods(element.getScaMethods());
            updatePsuAuthenticationResponse.setScaStatus(scaStatusMapper.toScaStatus(element.getConsentStatus()));

            return updatePsuAuthenticationResponse;
        }).orElse(null);

    }

    private Map<String, HrefType> toHrefType(Map<String, Link> links) {
        return links.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, value -> new HrefType(value.getValue().getHref())));
    }
}
