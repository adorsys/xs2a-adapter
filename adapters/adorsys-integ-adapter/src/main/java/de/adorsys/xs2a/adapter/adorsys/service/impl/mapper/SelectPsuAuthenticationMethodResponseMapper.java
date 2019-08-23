package de.adorsys.xs2a.adapter.adorsys.service.impl.mapper;

import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper
public interface SelectPsuAuthenticationMethodResponseMapper {

    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(AdorsysIntegSelectPsuAuthenticationMethodResponse response);

    default List<String> toListOfString(String str) {
        return Collections.singletonList(str);
    }
}
