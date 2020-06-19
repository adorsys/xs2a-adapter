package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.PeriodicPaymentInitiationWithStatusResponseTO;
import de.adorsys.xs2a.adapter.service.model.PeriodicPaymentInitiationInformationWithStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PeriodicPaymentInformationMapper {

    PeriodicPaymentInitiationInformationWithStatusResponse toPeriodicPaymentInitiationInformationWithStatusResponse(
        PeriodicPaymentInitiationWithStatusResponseTO to
    );
}
