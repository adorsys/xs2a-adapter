package de.adorsys.xs2a.adapter.commerzbank.mapper;

import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;

public interface RemittanceInformationStructuredMapper {
    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
