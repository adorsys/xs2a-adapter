package de.adorsys.xs2a.adapter.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.adorsys.xs2a.adapter.registry.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AspspCsvServiceImpl implements AspspCsvService {

    private final Logger LOG = LoggerFactory.getLogger(AspspCsvServiceImpl.class);

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
            LOG.warn("Exception occurred while indexes were being written into a CSV" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
