package de.adorsys.xs2a.adapter.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.adorsys.xs2a.adapter.registry.exception.RegistryIOException;
import de.adorsys.xs2a.adapter.registry.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AspspCsvServiceImpl implements AspspCsvService {

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

    private String toCsvString(AspspCsvRecord aspsp) {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(AspspCsvRecord.class).withoutQuoteChar();

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
        List<AspspCsvRecord> aspsps = new CsvMapper().readerWithTypedSchemaFor(AspspCsvRecord.class)
            .<AspspCsvRecord>readValues(csv)
            .readAll();

        return aspspMapper.toAspsps(aspsps);
    }
}
