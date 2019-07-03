package de.adorsys.xs2a.adapter.service.impl;

import java.util.Optional;

public interface AspspAdapterConfig extends Iterable<AspspAdapterConfigRecord> {
    Optional<AspspAdapterConfigRecord> getAspspAdapterConfigRecord(String bic);
}
