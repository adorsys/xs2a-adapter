package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.model.ScaStatus;
import de.adorsys.xs2a.adapter.service.model.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

public class StartScaProcessResponseUnicreditMapper {
    private final ChallengeDataMapper challengeDataMapper = Mappers.getMapper(ChallengeDataMapper.class);

    public StartScaProcessResponse toStartScaProcessResponse(UnicreditStartScaProcessResponse response) {
        return Optional.ofNullable(response)
                   .map(r -> {
                       StartScaProcessResponse startScaProcessResponse = new StartScaProcessResponse();

                       startScaProcessResponse.setScaStatus(toScaStatus(r));
                       startScaProcessResponse.setAuthorisationId(r.getAuthorisationId());
                       startScaProcessResponse.setScaMethods(r.getScaMethods());
                       startScaProcessResponse.setChosenScaMethod(r.getChosenScaMethod());
                       startScaProcessResponse.setChallengeData(challengeDataMapper.toChallengeData(r.getChallengeData()));
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
