package adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.mapper.ScaStatusMapper;
import de.adorsys.xs2a.adapter.service.model.ConsentStatus;
import de.adorsys.xs2a.adapter.service.model.ScaStatus;
import de.adorsys.xs2a.adapter.service.model.TransactionStatus;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScaStatusMapperTest {
    private static final ConsentStatus CONSENT_STATUS = ConsentStatus.VALID;
    private static final ScaStatus EXPECTED_SCA_STATUS_FOR_CONSENT_STATUS = ScaStatus.FINALISED;
    private static final ConsentStatus NULL_CONSENT_STATUS = null;

    private static final TransactionStatus TRANSACTION_STATUS = TransactionStatus.RJCT;
    private static final ScaStatus EXPECTED_SCA_STATUS_FOR_TRANSACTION_STATUS = ScaStatus.FAILED;
    private static final TransactionStatus NULL_TRANSACTION_STATUS = null;

    private ScaStatusMapper scaStatusMapper;

    @Before
    public void setUp() {
        scaStatusMapper = new ScaStatusMapper();
    }

    @Test
    public void toScaStatus_fromConsentStatus_Success() {
        ScaStatus scaStatus = scaStatusMapper.toScaStatus(CONSENT_STATUS);
        assertThat(scaStatus).isEqualTo(EXPECTED_SCA_STATUS_FOR_CONSENT_STATUS);
    }

    @Test(expected = RuntimeException.class)
    public void toScaStatus_fromConsentStatus_Fail_exceptionIsThrown() {
        scaStatusMapper.toScaStatus(NULL_CONSENT_STATUS);
    }

    @Test
    public void toScaStatus_fromTransactionStatus_Success() {
        ScaStatus scaStatus = scaStatusMapper.toScaStatus(TRANSACTION_STATUS);
        assertThat(scaStatus).isEqualTo(EXPECTED_SCA_STATUS_FOR_TRANSACTION_STATUS);
    }

    @Test(expected = RuntimeException.class)
    public void toScaStatus_fromTransactionStatus_Fail_exceptionIsThrown() {
        scaStatusMapper.toScaStatus(NULL_TRANSACTION_STATUS);
    }
}
