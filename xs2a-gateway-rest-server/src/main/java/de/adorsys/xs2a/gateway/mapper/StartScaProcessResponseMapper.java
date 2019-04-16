package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.StartScaprocessResponseTO;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import org.mapstruct.Mapper;

@Mapper
public interface StartScaProcessResponseMapper {
    StartScaprocessResponseTO toStartScaprocessResponseTO(StartScaProcessResponse startScaProcessResponse);
}
