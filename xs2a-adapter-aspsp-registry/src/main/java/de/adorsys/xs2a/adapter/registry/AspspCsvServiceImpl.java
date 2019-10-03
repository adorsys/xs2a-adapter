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

    private static final String CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH = PropertyUtil.readProperty("csv.aspsp.adapter.config.file.path");
    private static final String DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE = "aspsp-adapter-config.csv";

    private final Logger log = LoggerFactory.getLogger(AspspCsvServiceImpl.class);

    private AspspRepository aspspRepository;

    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public AspspCsvServiceImpl(AspspRepository aspspRepository) {
        this.aspspRepository = aspspRepository;
    }

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

    @Override
    public void importCsv(byte[] bytes) {
        List<Aspsp> aspsps;
        try {
            aspsps = readAllRecords(bytes);
        } catch (IOException e) {
            throw new RegistryIOException(e);
        }

        aspspRepository.deleteAll();
        aspspRepository.saveAll(aspsps);
    }

    @Override
    public void saveCsv() throws IOException {
        if (CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH == null || CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH.isEmpty()) {
            throw new RuntimeException("'csv.aspsp.adapter.config.file.path' property does not exist or has no value");
        } else {
            Files.write(Paths.get(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH), exportCsv());
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

    public List<Aspsp> readAllRecords(byte[] csv) throws IOException {
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

        List<AspspCsvRecord> aspsps = objectReader
            .<AspspCsvRecord>readValues(csv)
            .readAll();

        return aspspMapper.toAspsps(aspsps);
    }

    @Override
    public byte[] getCsvFileAsByteArray() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[8192];
        try (InputStream is = getCsvFileAsStream()) {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        }
    }

    private InputStream getCsvFileAsStream() throws FileNotFoundException {
        InputStream inputStream;

        if (CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH == null || CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH.isEmpty()) {
            inputStream = getResourceAsStream(DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE);
        } else {
            inputStream = getFileAsStream(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH);
        }
        return inputStream;
    }

    private InputStream getResourceAsStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    private InputStream getFileAsStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }
}
