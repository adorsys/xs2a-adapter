package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountReferenceTO;
import de.adorsys.xs2a.gateway.service.consent.AccountReference;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class AccountReferenceMapperTest {

    private static final String BBAN = "bban";
    private static final String IBAN = "iban";
    private static final String PAN = "pan";
    private static final String MSISDN = "msisdn";
    private static final String MASKED_PAN = "maskedPan";
    private static final String CURRENCY = "UAH";

    @Test
    public void toAccountReference() {
        AccountReference reference = Mappers.getMapper(AccountReferenceMapper.class).toAccountReference(buildAccountReference());

        assertThat(reference.getCurrency()).isEqualTo(CURRENCY);
        assertThat(reference.getBban()).isEqualTo(BBAN);
        assertThat(reference.getIban()).isEqualTo(IBAN);
        assertThat(reference.getMaskedPan()).isEqualTo(MASKED_PAN);
        assertThat(reference.getPan()).isEqualTo(PAN);
        assertThat(reference.getMsisdn()).isEqualTo(MSISDN);
    }

    static AccountReferenceTO buildAccountReference() {
        AccountReferenceTO reference = new AccountReferenceTO();
        reference.setBban(BBAN);
        reference.setIban(IBAN);
        reference.setPan(PAN);
        reference.setMsisdn(MSISDN);
        reference.setMaskedPan(MASKED_PAN);
        reference.setCurrency(CURRENCY);
        return reference;
    }
}