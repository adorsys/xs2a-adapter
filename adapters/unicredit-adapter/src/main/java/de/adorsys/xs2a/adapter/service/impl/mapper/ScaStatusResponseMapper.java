package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.ScaStatus;
import de.adorsys.xs2a.adapter.service.TransactionStatus;
import de.adorsys.xs2a.adapter.service.ais.ConsentStatus;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditAccountScaStatusResponse;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditPaymentScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ScaStatusResponseMapper {

    default ScaStatusResponse toScaStatusResponse(UnicreditAccountScaStatusResponse unicreditAccountScaStatusResponse) {
        ConsentStatus consentStatus = unicreditAccountScaStatusResponse.getConsentStatus();

        ScaStatus scaStatus;

        if (consentStatus == ConsentStatus.VALID) {
            scaStatus = ScaStatus.FINALISED;
        } else if (consentStatus == ConsentStatus.REJECTED) {
            scaStatus = ScaStatus.FAILED;
        } else {
            // TODO come up with better solution
            throw new RuntimeException();
        }

        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(scaStatus);

        return scaStatusResponse;
    }

    default ScaStatusResponse toScaStatusResponse(UnicreditPaymentScaStatusResponse unicreditPaymentScaStatusResponse) {
        TransactionStatus transactionStatus = unicreditPaymentScaStatusResponse.getTransactionStatus();

        ScaStatus scaStatus;

        if (transactionStatus == TransactionStatus.ACCP) {
            scaStatus = ScaStatus.FINALISED;
        } else if (transactionStatus == TransactionStatus.RJCT) {
            scaStatus = ScaStatus.FAILED;
        } else {
            // TODO come up with better solution
            throw new RuntimeException();
        }

        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(scaStatus);

        return scaStatusResponse;
    }
}
