package de.adorsys.xs2a.adapter.registry;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import de.adorsys.xs2a.adapter.registry.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class AspspCsvServiceImplTest {

    private LuceneAspspRepository luceneAspspRepository = new LuceneAspspRepository(new ByteBuffersDirectory());
    private AspspCsvService aspspCsvService = new AspspCsvServiceImpl(luceneAspspRepository);
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    @Test
    public void exportCsv() throws IOException {
        byte[] storedBytesTemplate = "81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\",CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080\n".getBytes();

        AspspCsvRecord aspspCsvRecord = new CsvMapper()
            .readerWithTypedSchemaFor(AspspCsvRecord.class)
            .readValue(storedBytesTemplate);

        luceneAspspRepository.save(aspspMapper.toAspsp(aspspCsvRecord));

        byte[] output = aspspCsvService.exportCsv();

        assertThat(Arrays.equals(output, storedBytesTemplate)).isTrue();
    }
}
