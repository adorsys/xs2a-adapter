package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.BankTO;
import de.adorsys.xs2a.adapter.service.Bank;
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
    private static final List<Bank> BANKS = banks();

    @Test
    public void toBankTOList() {
        List<BankTO> bankTOList = Mappers.getMapper(BankMapper.class).toBankTOList(BANKS);

        assertThat(bankTOList).isNotNull();
        assertThat(bankTOList).isNotEmpty();
        assertThat(bankTOList.size()).isEqualTo(BANKS.size());

        for (int i = 0; i < bankTOList.size(); i++) {
            BankTO bankTO = bankTOList.get(i);
            assertThat(bankTO).isNotNull();

            Bank record = BANKS.get(i);
            assertThat(bankTO.getName()).isEqualTo(record.getName());
            assertThat(bankTO.getBic()).isEqualTo(record.getBic());
        }
    }

    private static List<Bank> banks() {
        return Arrays.asList(
            newBank(FIRST_BANK_NAME, FIRST_BIC),
            newBank(SECOND_BANK_NAME, SECOND_BIC),
            newBank(THIRD_BANK_NAME, THIRD_BIC)
        );
    }

    private static Bank newBank(String name, String bic) {
        Bank bank = new Bank();
        bank.setName(name);
        bank.setBic(bic);
        return bank;
    }
}
