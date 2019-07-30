package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.PaymentInitiationStatusResponse200JsonTO;
import de.adorsys.xs2a.adapter.service.PaymentInitiationStatus;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentInitiationStatusMapper {
    PaymentInitiationStatusResponse200JsonTO toPaymentInitiationStatusResponse200Json(PaymentInitiationStatus status);

    PaymentInitiationStatus toPaymentInitiationStatus(PaymentInitiationStatusResponse200JsonTO to);
}
