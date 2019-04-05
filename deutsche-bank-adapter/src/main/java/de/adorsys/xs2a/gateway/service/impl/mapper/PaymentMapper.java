package de.adorsys.xs2a.gateway.service.impl.mapper;

import de.adorsys.xs2a.gateway.service.PaymentInitiationRequestResponse;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankPaymentInitiationResponse;
import de.adorsys.xs2a.gateway.service.impl.model.ObjectLink;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
    PaymentInitiationRequestResponse toPaymentInitiationRequestResponse(DeutscheBankPaymentInitiationResponse response);

    default String toString(ObjectLink objectLink) {
        return objectLink == null ? null : objectLink.getHref();
    }
}
