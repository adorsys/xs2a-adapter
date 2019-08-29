package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.ScaStatus;
import de.adorsys.xs2a.adapter.service.TransactionStatus;
import de.adorsys.xs2a.adapter.service.ais.ConsentStatus;

import java.util.EnumMap;

// Workaround for Berlin Group 1.1, as there is no Authorisation (SCA) as a separate model.
// That's why we get consent status and have to map it to SCA status.
class ScaStatusMapper {
    private static final EnumMap<ConsentStatus, ScaStatus> CONSENT_STATUS_TO_SCA_STATUS = new EnumMap<>(ConsentStatus.class);
    private static final EnumMap<TransactionStatus, ScaStatus> TRANSACTION_STATUS_TO_SCA_STATUS = new EnumMap<>(TransactionStatus.class);

    static {
        CONSENT_STATUS_TO_SCA_STATUS.put(ConsentStatus.RECEIVED, ScaStatus.RECEIVED);
        CONSENT_STATUS_TO_SCA_STATUS.put(ConsentStatus.VALID, ScaStatus.FINALISED);
        CONSENT_STATUS_TO_SCA_STATUS.put(ConsentStatus.REJECTED, ScaStatus.FAILED);
        CONSENT_STATUS_TO_SCA_STATUS.put(ConsentStatus.EXPIRED, ScaStatus.FAILED);
        CONSENT_STATUS_TO_SCA_STATUS.put(ConsentStatus.REVOKEDBYPSU, ScaStatus.FAILED);
        CONSENT_STATUS_TO_SCA_STATUS.put(ConsentStatus.TERMINATEDBYTPP, ScaStatus.FAILED);

        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.RCVD, ScaStatus.RECEIVED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.PDNG, ScaStatus.RECEIVED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACCC, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACCP, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACSC, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACSP, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACTC, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACWC, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACWP, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.ACFC, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.PATC, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.PART, ScaStatus.FINALISED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.RJCT, ScaStatus.FAILED);
        TRANSACTION_STATUS_TO_SCA_STATUS.put(TransactionStatus.CANC, ScaStatus.FAILED);
    }

    ScaStatus toScaStatus(ConsentStatus consentStatus) {
        ScaStatus scaStatus = CONSENT_STATUS_TO_SCA_STATUS.get(consentStatus);

        if (scaStatus == null) {
            throw new RuntimeException(String.format("Unknown consent status: [%s]", consentStatus));
        }

        return scaStatus;
    }

    ScaStatus toScaStatus(TransactionStatus transactionStatus) {
        ScaStatus scaStatus = TRANSACTION_STATUS_TO_SCA_STATUS.get(transactionStatus);

        if (scaStatus == null) {
            throw new RuntimeException(String.format("Unknown transaction status: [%s]", transactionStatus));
        }

        return scaStatus;
    }
}
