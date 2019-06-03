package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.PaymentInitiationStatusResponse200JsonTO;
import de.adorsys.xs2a.gateway.service.PaymentInitiationStatus;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentInitiationStatusMapper {
    PaymentInitiationStatusResponse200JsonTO toPaymentInitiationStatusResponse200Json(PaymentInitiationStatus status);
}
