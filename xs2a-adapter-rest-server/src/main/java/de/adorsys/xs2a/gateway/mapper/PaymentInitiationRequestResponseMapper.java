package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.PaymentInitationRequestResponse201TO;
import de.adorsys.xs2a.gateway.service.PaymentInitiationRequestResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentInitiationRequestResponseMapper {
    PaymentInitationRequestResponse201TO toPaymentInitationRequestResponse201TO(PaymentInitiationRequestResponse paymentInitiationRequestResponse);
}
