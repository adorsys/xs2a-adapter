package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.impl.model.DkbPaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationRequestResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeDataDkbMapper.class)
public interface PaymentInitiationRequestResponseDkbMapper {

    PaymentInitiationRequestResponse toPaymentInitiationRequestResponse(DkbPaymentInitiationRequestResponse response);
}
