package de.adorsys.xs2a.adapter.dkb.mapper;

import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.dkb.model.DkbPaymentInitiationRequestResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeDataDkbMapper.class)
public interface PaymentInitiationRequestResponseDkbMapper {

    PaymentInitationRequestResponse201 toPaymentInitiationRequestResponse(DkbPaymentInitiationRequestResponse response);
}
