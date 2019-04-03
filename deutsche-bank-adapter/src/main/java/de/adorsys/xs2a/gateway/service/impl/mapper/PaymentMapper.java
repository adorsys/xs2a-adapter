package de.adorsys.xs2a.gateway.service.impl.mapper;

import de.adorsys.xs2a.gateway.service.PaymentInitiationRequestResponse;
import de.adorsys.xs2a.gateway.service.impl.model.ObjectLink;
import de.adorsys.xs2a.gateway.service.impl.model.PaymentInitiationResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
    PaymentInitiationRequestResponse toBerlinGroupPaymentInitiationResponse(PaymentInitiationResponse response);

    default String toString(ObjectLink objectLink) {
        return objectLink == null ? null : objectLink.getHref();
    }
}
