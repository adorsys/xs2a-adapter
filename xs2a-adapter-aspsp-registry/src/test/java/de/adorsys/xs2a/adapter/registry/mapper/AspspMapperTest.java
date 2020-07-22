package de.adorsys.xs2a.adapter.registry.mapper;

import de.adorsys.xs2a.adapter.registry.AspspCsvRecord;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AspspMapperTest {

    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    private static final String ID = "42";
    private static final String ADAPTER_ID = "test-adapter";

    @Test
    void toAspspCsvRecord() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId(ID);
        aspsp.setAdapterId(ADAPTER_ID);

        AspspCsvRecord aspspCsvRecord = aspspMapper.toAspspCsvRecord(aspsp);

        assertThat(aspspCsvRecord).isNotNull();
        assertThat(aspspCsvRecord.getId()).isEqualTo(ID);
        assertThat(aspspCsvRecord.getAdapterId()).isEqualTo(ADAPTER_ID);
    }
}
