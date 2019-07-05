package de.adorsys.xs2a.adapter.service.impl;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.*;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class CsvAspspAdapterConfig implements AspspAdapterConfig {
    private static final String CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH_PROPERTY = "csv.aspsp.adapter.config.file.path";
    private static final String DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE = "aspsp-adapter-config.csv";

    private final List<AspspAdapterConfigRecord> records;
    private final Map<String, AspspAdapterConfigRecord> bicToRecord;

    private CsvAspspAdapterConfig(List<AspspAdapterConfigRecord> records) {
        this.records = Collections.unmodifiableList(records);
        bicToRecord = records.stream()
            .collect(toMap(AspspAdapterConfigRecord::getBic, identity()));
    }

    public CsvAspspAdapterConfig() {
        this(load());
    }

    private static List<AspspAdapterConfigRecord> load() {
        String filePath = System.getenv(CSV_ASPSP_ADAPTER_CONFIG_FILE_PATH_PROPERTY);

        InputStream inputStream;

        if (filePath == null || filePath.isEmpty()) {
            inputStream = getResourceAsStream(DEFAULT_CSV_ASPSP_ADAPTER_CONFIG_FILE);
        } else {
            inputStream = getFileAsStream(filePath);
        }

        List<AspspAdapterConfigRecord> records;
        try {
            records = new CsvMapper().readerWithTypedSchemaFor(AspspAdapterConfigRecord.class)
                .<AspspAdapterConfigRecord>readValues(inputStream)
                .readAll();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return records;
    }

    private static InputStream getResourceAsStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    private static InputStream getFileAsStream(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Optional<AspspAdapterConfigRecord> getAspspAdapterConfigRecord(String bic) {
        return Optional.ofNullable(bicToRecord.get(bic));
    }

    @Override
    public Iterator<AspspAdapterConfigRecord> iterator() {
        return records.iterator();
    }
}
