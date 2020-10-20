package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.model.PaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixPaymentInitiationWithStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CrealogixMapper {

    PaymentInitiationWithStatusResponse toPaymentInitiationWithStatusResponse(CrealogixPaymentInitiationWithStatusResponse value);
}
