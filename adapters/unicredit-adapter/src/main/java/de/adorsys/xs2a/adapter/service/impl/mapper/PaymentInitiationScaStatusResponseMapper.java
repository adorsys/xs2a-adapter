package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.model.PaymentInitiationScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationStatus;
import de.adorsys.xs2a.adapter.service.model.TransactionStatus;

import java.util.Optional;

public class PaymentInitiationScaStatusResponseMapper {
    private final ScaStatusMapper scaStatusMapper = new ScaStatusMapper();

    public PaymentInitiationScaStatusResponse toScaStatusResponse(PaymentInitiationStatus paymentInitiationStatus) {
        TransactionStatus transactionStatus = Optional.ofNullable(paymentInitiationStatus)
                                                  .map(PaymentInitiationStatus::getTransactionStatus)
                                                  .orElse(null);

        return new PaymentInitiationScaStatusResponse(scaStatusMapper.toScaStatus(transactionStatus));
    }
}
