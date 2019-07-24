package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.PaymentInitiationWithStatusResponseTO;
import de.adorsys.xs2a.adapter.service.SinglePaymentInitiationInformationWithStatusResponse;
import org.mapstruct.Mapper;

@Mapper(uses = {AddressMapper.class, AmountMapper.class, AccountReferenceMapper.class})
public interface SinglePaymentInformationMapper {
    PaymentInitiationWithStatusResponseTO toPaymentInitiationSctWithStatusResponse(SinglePaymentInitiationInformationWithStatusResponse response);

    SinglePaymentInitiationInformationWithStatusResponse toSinglePaymentInitiationInformationWithStatusResponse(PaymentInitiationWithStatusResponseTO to);
}
