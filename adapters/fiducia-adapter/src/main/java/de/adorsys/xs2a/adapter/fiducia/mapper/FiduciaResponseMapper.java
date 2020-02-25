package de.adorsys.xs2a.adapter.fiducia.mapper;

import de.adorsys.xs2a.adapter.fiducia.model.FiduciaStartScaProcessResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaUpdatePsuDataResponse;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.service.model.StartScaProcessResponse;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper
public interface FiduciaResponseMapper {
    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(FiduciaUpdatePsuDataResponse value);

    default List<String> toListOfStrings(String challengeData) {
        return Collections.singletonList(challengeData);
    }

    StartScaProcessResponse toStartScaProcessResponse(FiduciaStartScaProcessResponse value);
}
