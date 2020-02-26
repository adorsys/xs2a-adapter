package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.ScaStatus;
import de.adorsys.xs2a.adapter.service.model.StartScaProcessResponse;

import java.util.Optional;

public class StartScaProcessResponseUnicreditMapper {

    public StartScaProcessResponse toStartScaProcessResponse(UnicreditStartScaProcessResponse response) {
        return Optional.ofNullable(response)
                   .map(r -> {
                       StartScaProcessResponse startScaProcessResponse = new StartScaProcessResponse();

                       startScaProcessResponse.setScaStatus(toScaStatus(r));
                       startScaProcessResponse.setAuthorisationId(r.getAuthorisationId());
                       startScaProcessResponse.setScaMethods(r.getScaMethods());
                       startScaProcessResponse.setChosenScaMethod(r.getChosenScaMethod());
                       startScaProcessResponse.setChallengeData(r.getChallengeData());
                       startScaProcessResponse.setLinks(r.getLinks());
                       startScaProcessResponse.setPsuMessage(r.getPsuMessage());

                       return startScaProcessResponse;
                   })
                   .orElse(null);
    }

    private ScaStatus toScaStatus(UnicreditStartScaProcessResponse response) {
        ScaStatus scaStatus;

        if (response.isSelectScaMethodStage()) {
            scaStatus = ScaStatus.PSUAUTHENTICATED;
        } else if (response.isChosenScaMethodStage()) {
            scaStatus = ScaStatus.SCAMETHODSELECTED;
        } else {
            scaStatus = null;
        }

        return scaStatus;
    }
}
