package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.model.DkbChallengeData;
import de.adorsys.xs2a.adapter.service.model.ChallengeData;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper
public interface ChallengeDataDkbMapper {

    ChallengeData toChallengeData(DkbChallengeData challengeData);

    default List<String> toListOfString(String str) {
        return Collections.singletonList(str);
    }
}
