package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.pis.PaymentInitiationSctWithStatusResponse;
import de.adorsys.xs2a.gateway.service.SinglePaymentInitiationInformationWithStatusResponse;
import org.mapstruct.Mapper;

@Mapper(uses = {AddressMapper.class, AmountMapper.class, AccountReferenceMapper.class})
public interface SinglePaymentInformationMapper {
    PaymentInitiationSctWithStatusResponse toPaymentInitiationSctWithStatusResponse(SinglePaymentInitiationInformationWithStatusResponse response);
}
