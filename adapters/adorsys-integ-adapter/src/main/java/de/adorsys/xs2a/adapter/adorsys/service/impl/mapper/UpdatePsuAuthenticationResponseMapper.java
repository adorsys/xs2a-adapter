package de.adorsys.xs2a.adapter.adorsys.service.impl.mapper;

import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegUpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper
public interface UpdatePsuAuthenticationResponseMapper {

    UpdatePsuAuthenticationResponse toUpdatePsuAuthenticationResponse(AdorsysIntegUpdatePsuAuthenticationResponse response);

    default List<String> toListOfString(String str) {
        return Collections.singletonList(str);
    }
}
