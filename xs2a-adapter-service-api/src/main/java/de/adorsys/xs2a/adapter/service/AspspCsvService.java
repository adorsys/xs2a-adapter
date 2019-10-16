package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.List;

public interface AspspCsvService {

    /**
     * Returns an array of bytes that contains all indexes which are currently
     * stored with Lucene.
     *
     * @return array of bytes with all Lucene indexes
     * @throws RuntimeException if Aspsp data writing into String fails
     */
    byte[] exportCsv();

    /**
     * Saves uploaded Aspsps into the current Lucene storage.
     *
     * @param file is a csv file with aspsp details information
     */
    void importCsv(byte[] file);

    /**
     * Reads the input csv file and converts its content into a list of Aspsp objects.
     *
     * @param csv is a file with aspsp details information
     * @return list of Aspsp objects
     */
    List<Aspsp> readAllRecords(byte[] csv);

    /**
     * Saves all changes of Lucene indexes into the specified adapter configuration CSV of Aspsps.
     */
    void saveCsv();

    /**
     * Converts a file, set as the source of Aspsp details records, into the array of bytes.
     *
     * @return array of bytes converted from the specified source file
     */
    byte[] getCsvFileAsByteArray();
}
