package de.adorsys.xs2a.adapter.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.registry.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.PropertyUtil;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AspspCsvServiceImpl implements AspspCsvService {

    static final String CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH = "csv.aspsp.adapter.config.file.path";
    private static final String DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE = "aspsp-adapter-config.csv";

    private final Logger log = LoggerFactory.getLogger(AspspCsvServiceImpl.class);
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    private AspspRepository aspspRepository;

    public AspspCsvServiceImpl(AspspRepository aspspRepository) {
        this.aspspRepository = aspspRepository;
    }

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
    @Override
    public byte[] exportCsv() {
        List<Aspsp> storage = aspspRepository.findAll();

        return storage
            .stream()
            .map(aspspMapper::toAspspCsvRecord)
            .map(this::toCsvString)
            .collect(Collectors.joining())
            .getBytes();
    }

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
    @Override
    public void importCsv(byte[] file) {
        List<Aspsp> aspsps = readAllRecords(file);
        aspspRepository.deleteAll();
        aspspRepository.saveAll(aspsps);
    }

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
     * @throws RegistryIOException if writing into the file fails
     * @throws RuntimeException if "csv.aspsp.adapter.config.file.path" property does not
     * exist or has an empty value
     */
    @Override
    public void saveCsv() {
        String csvConfigFileProperty = PropertyUtil.readProperty(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH);

        if (csvConfigFileProperty.isEmpty()) {
            throw new RuntimeException(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH + " property does not exist or has no value");
        } else {
            try {
                Files.write(Paths.get(csvConfigFileProperty), exportCsv());
            } catch (IOException e) {
                throw new RegistryIOException(e);
            }
        }
    }

    private String toCsvString(AspspCsvRecord aspsp) {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(AspspCsvRecord.class).withoutQuoteChar();

        if (aspsp.getAspspName() == null) {
            aspsp.setAspspName("");
        }

        if (aspsp.getAspspName().contains(",")) {
            String columnName = "aspspName";
            int nameColumnIndex = schema.column(columnName).getIndex();
            schema = mapper
                .configure(CsvGenerator.Feature.STRICT_CHECK_FOR_QUOTING, true)
                .schemaFor(AspspCsvRecord.class)
                .rebuild()
                .replaceColumn(nameColumnIndex, new CsvSchema.Column(nameColumnIndex, columnName))
                .build();
        }

        try {
            return mapper.writer(schema).writeValueAsString(aspsp);
        } catch (JsonProcessingException e) {
            log.warn("Exception occurred while indexes were being written into a CSV: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the input csv file and converts its content into a list of Aspsp objects.
     * <p>
     * Accepts a csv file as an array of bytes and creates AspspCsvRecord objects based
     * on that data with the help of CsvMapper. Then it turns AspspCsvRecord objects
     * into Aspsp entities by mapping the appropriate fields with AspspMapper.
     *
     * @param csv is a file with aspsp details information
     * @return list of Aspsp objects
     * @throws RegistryIOException if reading bytes process fails
     */
    public List<Aspsp> readAllRecords(byte[] csv) {
        ObjectReader objectReader = new CsvMapper()
            .readerWithTypedSchemaFor(AspspCsvRecord.class)
            .withHandler(new DeserializationProblemHandler() {
                @Override
                public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg) {
                    if (targetType.isEnum()) {
                        return Enum.valueOf((Class<Enum>) targetType, valueToConvert.trim().toUpperCase());
                    }

                    return DeserializationProblemHandler.NOT_HANDLED;
                }
            });

        List<AspspCsvRecord> aspsps;
        try {
            aspsps = objectReader
                .<AspspCsvRecord>readValues(csv)
                .readAll();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }

        return aspspMapper.toAspsps(aspsps);
    }

    /**
     * Converts a file, set as the source of Aspsp details records, into the array of bytes.
     * <p>
     * Reads, specified within the Configuration Properties source, file using
     * {@link java.io.InputStream} and converts it into bytes via
     * {@link java.io.BufferedOutputStream}
     *
     * @return array of bytes converted from the specified source file
     * @throws RegistryIOException if reading data from file fails
     */
    @Override
    public byte[] getCsvFileAsByteArray() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[8192];
        try (InputStream is = getCsvFileAsStream()) {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }
    }

    private InputStream getCsvFileAsStream() {
        String csvConfigFileProperty = PropertyUtil.readProperty(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH);

        InputStream inputStream;

        if (csvConfigFileProperty.isEmpty()) {
            inputStream = getResourceAsStream(DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE);
        } else {
            inputStream = getFileAsStream(csvConfigFileProperty);
        }
        return inputStream;
    }

    private InputStream getResourceAsStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    private InputStream getFileAsStream(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RegistryIOException(e);
        }
    }
}
