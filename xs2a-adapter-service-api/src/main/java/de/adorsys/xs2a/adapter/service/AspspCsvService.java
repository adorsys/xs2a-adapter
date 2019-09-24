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
     * @throws RuntimeException if Aspsp data writing into String fails
     */
    byte[] exportCsv();

    /**
     * Saves uploaded Aspsps into the current Lucene storage.
     * <p>
     * Accepts a csv file as the array of bites. The method converts it into the list
     * of aspsps, remove all existing records from Lucene and saves the pushed objects into the
     * repository. Can produce RegistryIOException (a type of RuntimeException) while
     * converting bites into objects.
     *
     * @param file is a csv file with aspsps details information
     * @throws RegistryIOException if converting array of bytes into Aspsp object fails
     */
    void importCsv(byte[] file);
}
