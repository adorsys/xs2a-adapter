package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.io.IOException;
import java.util.List;

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
     * @param file is a csv file with aspsp details information
     * @throws RegistryIOException if converting array of bytes into Aspsp object fails
     */
    void importCsv(byte[] file);

    /**
     * Reads the input csv file and converts its content into a list of Aspsp objects.
     * <p>
     * Accepts a csv file as an array of bytes and creates AspspCsvRecord objects based
     * on that data with the help of CsvMapper. Then it turns AspspCsvRecord objects
     * into Aspsp entities by mapping the appropriate fields with AspspMapper.
     *
     * @param csv is a file with aspsp details information
     * @return list of Aspsp objects
     * @throws IOException if reading bytes process fails
     */
    List<Aspsp> readAllRecords(byte[] csv) throws IOException;
}
