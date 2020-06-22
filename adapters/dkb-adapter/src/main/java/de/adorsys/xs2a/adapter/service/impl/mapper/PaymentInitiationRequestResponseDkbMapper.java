package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.service.impl.model.DkbPaymentInitiationRequestResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeDataDkbMapper.class)
public interface PaymentInitiationRequestResponseDkbMapper {

    PaymentInitationRequestResponse201 toPaymentInitiationRequestResponse(DkbPaymentInitiationRequestResponse response);
}
