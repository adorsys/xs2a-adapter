package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.model.ChallengeData;
import de.adorsys.xs2a.adapter.service.model.OtpFormat;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditChallengeData;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChallengeDataMapperTest {
    private static final byte[] IMAGE = "testImage".getBytes();
    private static final List<String> DATA = Collections.singletonList("testData");
    private static final String IMAGE_LINK = "testImageLink";
    private static final Integer OTP_MAX_LENGTH = 10;
    private static final OtpFormat OTP_FORMAT = OtpFormat.CHARACTERS;
    private static final String ADDITIONAL_INFORMATION = "testAdditionalInformation";

    private static final UnicreditChallengeData UNICREDIT_CHALLENGE_DATA = buildUnicreditChallengeData();
    private static final ChallengeData EXPECTED_CHALLENGE_DATA = buildChallengeData();

    private ChallengeDataMapper challengeDataMapper;

    @Before
    public void setUp() {
        challengeDataMapper = Mappers.getMapper(ChallengeDataMapper.class);
    }

    @Test
    public void toChallengeData_Success() {
        ChallengeData challengeData = challengeDataMapper.toChallengeData(UNICREDIT_CHALLENGE_DATA);
        assertThat(challengeData).isEqualToComparingFieldByField(EXPECTED_CHALLENGE_DATA);
    }

    private static UnicreditChallengeData buildUnicreditChallengeData() {
        UnicreditChallengeData unicreditChallengeData = new UnicreditChallengeData();

        unicreditChallengeData.setImage(IMAGE);
        unicreditChallengeData.setData(DATA);
        unicreditChallengeData.setImageLink(IMAGE_LINK);
        unicreditChallengeData.setOtpMaxLength(OTP_MAX_LENGTH);
        unicreditChallengeData.setOtpFormat(OTP_FORMAT);
        unicreditChallengeData.setAdditionalInformation(ADDITIONAL_INFORMATION);

        return unicreditChallengeData;
    }

    private static ChallengeData buildChallengeData() {
        ChallengeData challengeData = new ChallengeData();

        challengeData.setImage(IMAGE);
        challengeData.setData(DATA);
        challengeData.setImageLink(IMAGE_LINK);
        challengeData.setOtpMaxLength(OTP_MAX_LENGTH);
        challengeData.setOtpFormat(OTP_FORMAT);
        challengeData.setAdditionalInformation(ADDITIONAL_INFORMATION);

        return challengeData;
    }

}
