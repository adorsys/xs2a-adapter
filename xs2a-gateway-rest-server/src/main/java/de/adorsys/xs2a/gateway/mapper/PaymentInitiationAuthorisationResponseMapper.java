package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.Authorisations;
import de.adorsys.xs2a.gateway.service.PaymentInitiationAuthorisationResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentInitiationAuthorisationResponseMapper {

    Authorisations toAuthorisations(PaymentInitiationAuthorisationResponse response);
}
