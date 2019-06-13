package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ScaStatusResponseTO;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ScaStatusResponseMapper {
    ScaStatusResponseTO toScaStatusResponseTO(ScaStatusResponse scaStatusResponse);
}
