package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AccountReferenceTO;
import de.adorsys.xs2a.adapter.service.AccountReference;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountReferenceMapperTest {

    private static final String BBAN = "bban";
    private static final String IBAN = "iban";
    private static final String PAN = "pan";
    private static final String MSISDN = "msisdn";
    private static final String MASKED_PAN = "maskedPan";
    private static final String CURRENCY = "UAH";

    @Test
    public void toAccountReference() {
        AccountReference reference = Mappers.getMapper(AccountReferenceMapper.class).toAccountReference(buildAccountReferenceTO());

        assertThat(reference.getCurrency()).isEqualTo(CURRENCY);
        assertThat(reference.getBban()).isEqualTo(BBAN);
        assertThat(reference.getIban()).isEqualTo(IBAN);
        assertThat(reference.getMaskedPan()).isEqualTo(MASKED_PAN);
        assertThat(reference.getPan()).isEqualTo(PAN);
        assertThat(reference.getMsisdn()).isEqualTo(MSISDN);
    }

    @Test
    public void toAccountReferenceTO() {
        AccountReferenceTO accountReferenceTO = Mappers.getMapper(AccountReferenceMapper.class).toAccountReferenceTO(buildAccountReference());

        assertThat(accountReferenceTO).isNotNull();
        assertThat(accountReferenceTO.getCurrency()).isEqualTo(CURRENCY);
        assertThat(accountReferenceTO.getBban()).isEqualTo(BBAN);
        assertThat(accountReferenceTO.getIban()).isEqualTo(IBAN);
        assertThat(accountReferenceTO.getMaskedPan()).isEqualTo(MASKED_PAN);
        assertThat(accountReferenceTO.getPan()).isEqualTo(PAN);
        assertThat(accountReferenceTO.getMsisdn()).isEqualTo(MSISDN);
    }

    static AccountReferenceTO buildAccountReferenceTO() {
        AccountReferenceTO reference = new AccountReferenceTO();
        reference.setBban(BBAN);
        reference.setIban(IBAN);
        reference.setPan(PAN);
        reference.setMsisdn(MSISDN);
        reference.setMaskedPan(MASKED_PAN);
        reference.setCurrency(CURRENCY);
        return reference;
    }

    static AccountReference buildAccountReference() {
        AccountReference reference = new AccountReference();
        reference.setBban(BBAN);
        reference.setIban(IBAN);
        reference.setPan(PAN);
        reference.setMsisdn(MSISDN);
        reference.setMaskedPan(MASKED_PAN);
        reference.setCurrency(CURRENCY);
        return reference;
    }
}
