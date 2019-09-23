package de.adorsys.xs2a.adapter.service;

public interface AspspCsvService {

    /**
     * Returns an array of bytes that contains all indexes which are currently
     * stored with Lucene.
     * <p>
     * The method reads all Aspsp objects from the existing registry, maps it into
     * AspspCsvRecord and converts those into an array of bytes for further transferring
     * to a front-end as a CSV file. Jackson is being used for turning an AspspCsvRecord
     * object into a CSV line, RuntimeException can be thrown during this operation
     * if processing fails.
     *
     * @return array of bytes with all Lucene indexes
     */

    byte[] exportCsv();

    void importCsv();
}
