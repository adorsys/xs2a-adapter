package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.List;

public interface AspspSearchService extends AspspReadOnlyRepository {
    List<Aspsp> findByIban(String iban, String after, int size);
}
