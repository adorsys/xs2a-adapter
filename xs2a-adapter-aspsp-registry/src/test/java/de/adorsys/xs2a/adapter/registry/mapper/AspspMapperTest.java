package de.adorsys.xs2a.adapter.registry.mapper;

import de.adorsys.xs2a.adapter.registry.AspspCsvRecord;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class AspspMapperTest {

    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    @Test
    public void toAspspCsvRecord() {
        Aspsp aspsp = new Aspsp();
        aspsp.setId("42");
        aspsp.setAdapterId("test-adapter");

        AspspCsvRecord aspspCsvRecord = aspspMapper.toAspspCsvRecord(aspsp);

        assertThat(aspspCsvRecord).isNotNull();
        assertThat(aspspCsvRecord).hasFieldOrPropertyWithValue("id", "42");
        assertThat(aspspCsvRecord).hasFieldOrPropertyWithValue("adapterId", "test-adapter");
    }
}
