package de.adorsys.xs2a.adapter.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.adorsys.xs2a.adapter.registry.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class AspspCsvServiceImpl implements AspspCsvService {

    private LuceneAspspRepository luceneAspspRepository;
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public AspspCsvServiceImpl(LuceneAspspRepository luceneAspspRepository) {
        this.luceneAspspRepository = luceneAspspRepository;
    }

    @Override
    public byte[] exportCsv() {
        List<Aspsp> storage = luceneAspspRepository.findAll();

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
            e.printStackTrace();
            return "Exception occurred";
        }
    }
}
