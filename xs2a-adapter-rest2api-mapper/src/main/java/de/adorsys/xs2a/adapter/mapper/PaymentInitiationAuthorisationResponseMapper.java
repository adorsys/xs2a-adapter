package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AuthorisationsTO;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationAuthorisationResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentInitiationAuthorisationResponseMapper {

    AuthorisationsTO toAuthorisationsTO(PaymentInitiationAuthorisationResponse response);

    PaymentInitiationAuthorisationResponse toPaymentInitiationAuthorisationResponse(AuthorisationsTO to);
}
