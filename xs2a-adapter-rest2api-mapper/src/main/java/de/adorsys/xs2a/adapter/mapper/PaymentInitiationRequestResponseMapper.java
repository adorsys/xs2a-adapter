package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.PaymentInitationRequestResponse201TO;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationRequestResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentInitiationRequestResponseMapper {
    PaymentInitationRequestResponse201TO toPaymentInitationRequestResponse201TO(PaymentInitiationRequestResponse paymentInitiationRequestResponse);

    PaymentInitiationRequestResponse toPaymentInitiationRequestResponse(PaymentInitationRequestResponse201TO to);
}
