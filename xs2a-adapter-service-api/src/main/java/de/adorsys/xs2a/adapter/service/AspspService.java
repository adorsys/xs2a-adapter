package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.List;

public interface AspspService {

    Aspsp create(Aspsp aspsp);

    Aspsp update(Aspsp aspsp);

    void deleteById(String aspspId);

    List<Aspsp> readAll();

    void importAspsps(List<Aspsp> aspsps);
}
