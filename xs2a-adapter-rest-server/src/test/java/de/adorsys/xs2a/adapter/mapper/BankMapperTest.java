package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.BankTO;
import de.adorsys.xs2a.adapter.service.impl.AspspAdapterConfigRecord;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BankMapperTest {
    private static final String FIRST_BANK_NAME = "Bank 1";
    private static final String SECOND_BANK_NAME = "Bank 2";
    private static final String THIRD_BANK_NAME = "Bank 3";
    private static final String FIRST_BIC = "111";
    private static final String SECOND_BIC = "222";
    private static final String THIRD_BIC = "333";
    private static final List<AspspAdapterConfigRecord> CONFIG_RECORDS = configRecords();

    @Test
    public void toBankTOList() {
        List<BankTO> bankTOList = Mappers.getMapper(BankMapper.class).toBankTOList(CONFIG_RECORDS);

        assertThat(bankTOList).isNotNull();
        assertThat(bankTOList).isNotEmpty();
        assertThat(bankTOList.size()).isEqualTo(CONFIG_RECORDS.size());

        for (int i = 0; i < bankTOList.size(); i++) {
            BankTO bankTO = bankTOList.get(i);
            assertThat(bankTO).isNotNull();

            AspspAdapterConfigRecord record = CONFIG_RECORDS.get(i);
            assertThat(bankTO.getName()).isEqualTo(record.getAspspName());
            assertThat(bankTO.getBic()).isEqualTo(record.getBic());
        }
    }

    private static List<AspspAdapterConfigRecord> configRecords() {
        return Arrays.asList(
            newAspspAdapterConfigRecord(FIRST_BANK_NAME, FIRST_BIC),
            newAspspAdapterConfigRecord(SECOND_BANK_NAME, SECOND_BIC),
            newAspspAdapterConfigRecord(THIRD_BANK_NAME, THIRD_BIC)
        );
    }

    private static AspspAdapterConfigRecord newAspspAdapterConfigRecord(String name, String bic) {
        AspspAdapterConfigRecord record = new AspspAdapterConfigRecord();
        record.setAspspName(name);
        record.setBic(bic);
        return record;
    }
}
