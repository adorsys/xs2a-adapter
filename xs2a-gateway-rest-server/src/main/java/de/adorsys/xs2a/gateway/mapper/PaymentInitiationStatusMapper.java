package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.pis.PaymentInitiationStatusResponse200Json;
import de.adorsys.xs2a.gateway.service.PaymentInitiationStatus;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentInitiationStatusMapper {
    PaymentInitiationStatusResponse200Json toPaymentInitiationStatusResponse200Json(PaymentInitiationStatus status);
}
