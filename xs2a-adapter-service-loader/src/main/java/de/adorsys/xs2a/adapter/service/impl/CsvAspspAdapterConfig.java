package de.adorsys.xs2a.adapter.service.impl;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class CsvAspspAdapterConfig implements AspspAdapterConfig {

    private final List<AspspAdapterConfigRecord> records;
    private final Map<String, AspspAdapterConfigRecord> bicToRecord;

    private CsvAspspAdapterConfig(List<AspspAdapterConfigRecord> records) {
        this.records = Collections.unmodifiableList(records);
        bicToRecord = records.stream()
            .collect(toMap(AspspAdapterConfigRecord::getBic, identity()));
    }

    public static CsvAspspAdapterConfig fromDefaultCsvFile() {
        return fromFile("aspsp-adapter-config.csv");
    }

    public static CsvAspspAdapterConfig fromFile(String fileName) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        List<AspspAdapterConfigRecord> records;
        try {
            records = new CsvMapper().readerWithTypedSchemaFor(AspspAdapterConfigRecord.class)
                .<AspspAdapterConfigRecord>readValues(inputStream)
                .readAll();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new CsvAspspAdapterConfig(records);
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
