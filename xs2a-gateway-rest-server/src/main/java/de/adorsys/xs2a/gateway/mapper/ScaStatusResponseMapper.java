package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.ScaStatusResponseTO;
import de.adorsys.xs2a.gateway.service.model.ScaStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ScaStatusResponseMapper {
    ScaStatusResponseTO toScaStatusResponseTO(ScaStatusResponse scaStatusResponse);
}
