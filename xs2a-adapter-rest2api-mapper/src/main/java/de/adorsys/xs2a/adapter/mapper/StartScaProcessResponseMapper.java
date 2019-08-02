package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.StartScaprocessResponseTO;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import org.mapstruct.Mapper;

@Mapper
public interface StartScaProcessResponseMapper {
    StartScaprocessResponseTO toStartScaprocessResponseTO(StartScaProcessResponse startScaProcessResponse);

    StartScaProcessResponse toStartScaProcessResponse(StartScaprocessResponseTO to);
}
