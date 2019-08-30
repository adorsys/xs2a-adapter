package de.adorsys.xs2a.adapter.adorsys.service.impl.mapper;

import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegPaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationRequestResponse;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper
public interface PaymentInitiationRequestResponseMapper {

    PaymentInitiationRequestResponse toPaymentInitiationRequestResponse(AdorsysIntegPaymentInitiationRequestResponse response);

    default List<String> toListOfString(String str) {
        return Collections.singletonList(str);
    }
}
