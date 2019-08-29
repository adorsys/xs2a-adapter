package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.ChallengeData;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditChallengeData;
import org.mapstruct.Mapper;

@Mapper
public interface ChallengeDataMapper {

    ChallengeData toChallengeData(UnicreditChallengeData unicreditChallengeData);
}
