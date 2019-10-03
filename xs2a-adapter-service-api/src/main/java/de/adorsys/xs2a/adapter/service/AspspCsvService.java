package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.io.IOException;
import java.util.List;

public interface AspspCsvService {

    /**
     * Returns an array of bytes that contains all indexes which are currently
     * stored with Lucene.
     * <p>
     * The method reads all {@link Aspsp} objects from the existing registry, maps it into
     * AspspCsvRecord and converts those into an array of bytes for further transferring
     * to a front-end as a CSV file. Jackson is being used for turning an AspspCsvRecord
     * object into a CSV line.
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

    /**
     * Saves all changes of Lucene indexes, that were made via Registry Manager UI, into
     * the specified adapter configuration CSV of Aspsps.
     * <p>
     * Replaces the current records of Aspsps withing configured file with new entries
     * that were created via Registry UI (manually or by importing a new CSV).
     * The original file is searched under "csv.aspsp.adapter.config.file.path" property.
     * <p>
     * {@link java.nio.file.Files} and {@link java.nio.file.Paths} are used for
     * re-writing the CSV
     *
     * @throws IOException if writing into the file fails
     * @throws RuntimeException if "csv.aspsp.adapter.config.file.path" property does not
     * exist or has an empty value
     */
    void saveCsv() throws IOException;

    /**
     * Converts a file, set as the source of Aspsp details records, into the array of bytes.
     * <p>
     * Reads, specified within the Configuration Properties source, file using
     * {@link java.io.InputStream} and converts it into bytes via
     * {@link java.io.BufferedOutputStream}
     *
     * @return array of bytes converted from the specified source file
     * @throws IOException if reading data from file fails
     */
    byte[] getCsvFileAsByteArray() throws IOException;
}
