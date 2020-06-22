package de.adorsys.xs2a.adapter.fiducia.mapper;

import de.adorsys.xs2a.adapter.api.model.SelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.api.model.StartScaprocessResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaStartScaProcessResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaUpdatePsuDataResponse;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper
public interface FiduciaResponseMapper {
    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(FiduciaUpdatePsuDataResponse value);

    default List<String> toListOfStrings(String challengeData) {
        if (challengeData == null) {
            return null;
        }
        return Collections.singletonList(challengeData);
    }

    StartScaprocessResponse toStartScaProcessResponse(FiduciaStartScaProcessResponse value);
}
