package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.List;

public interface AspspService {

    /**
     * Saves Aspsp instance into the Lucene storage
     *
     * @param aspsp instance of {@link Aspsp}
     * @return saved instance of {@link Aspsp}
     */
    Aspsp create(Aspsp aspsp);

    /**
     * Updates Aspsp record in the Lucene storage
     *
     * @param aspsp instance of {@link Aspsp}
     * @return updated instance of {@link Aspsp}
     */
    Aspsp update(Aspsp aspsp);

    /**
     * Deletes Aspsp record by its identifier from Lucene storage
     *
     * @param aspspId Aspsp identifier
     */
    void deleteById(String aspspId);

    /**
     * Reads all Aspsp records from Lucene storage
     *
     * @return list of {@link Aspsp} instances
     */
    List<Aspsp> readAll();

    /**
     * Replaces all the Aspsp records in Lucene storage
     * with the list of {@link Aspsp} instances
     *
     * @param aspsps list of {@link Aspsp} instances
     */
    void importAspsps(List<Aspsp> aspsps);
}
