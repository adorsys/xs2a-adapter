package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.BankTO;
import de.adorsys.xs2a.gateway.service.Bank;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BankMapperTest {
    private static final String FIRST_BANK_NAME = "Bank 1";
    private static final String SECOND_BANK_NAME = "Bank 2";
    private static final String THIRD_BANK_NAME = "Bank 3";
    private static final Set<String> FIRST_BANK_CODES = new HashSet<>(Arrays.asList("111", "222", "333"));
    private static final Set<String> SECOND_BANK_CODES = new HashSet<>(Arrays.asList("444", "555", "666"));
    private static final Set<String> THIRD_BANK_CODES = new HashSet<>(Arrays.asList("777", "888", "999"));
    private static final List<Bank> BANK_LIST = buildBankList();

    @Test
    public void toBankTOList() {
        List<BankTO> bankTOList = Mappers.getMapper(BankMapper.class).toBankTOList(BANK_LIST);

        assertThat(bankTOList).isNotNull();
        assertThat(bankTOList).isNotEmpty();
        assertThat(bankTOList.size()).isEqualTo(BANK_LIST.size());

        for (int i = 0; i < bankTOList.size(); i++) {
            BankTO bankTO = bankTOList.get(i);
            assertThat(bankTO).isNotNull();

            Bank bank = BANK_LIST.get(i);
            assertThat(bankTO).isEqualToComparingFieldByField(bank);
        }
    }

    private static List<Bank> buildBankList() {
        return Arrays.asList(
            new Bank(FIRST_BANK_NAME, FIRST_BANK_CODES),
            new Bank(SECOND_BANK_NAME, SECOND_BANK_CODES),
            new Bank(THIRD_BANK_NAME, THIRD_BANK_CODES)
        );
    }
}
