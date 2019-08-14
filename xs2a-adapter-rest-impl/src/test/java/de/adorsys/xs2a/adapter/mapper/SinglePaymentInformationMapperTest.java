package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AccountReferenceTO;
import de.adorsys.xs2a.adapter.model.AddressTO;
import de.adorsys.xs2a.adapter.model.AmountTO;
import de.adorsys.xs2a.adapter.model.PaymentInitiationWithStatusResponseTO;
import de.adorsys.xs2a.adapter.service.*;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class SinglePaymentInformationMapperTest {
    private static final AccountReference DEBTOR_ACCOUNT = AccountReferenceMapperTest.buildAccountReference();
    private static final Amount INSTRUCTED_AMOUNT = AmountMapperTest.buildAmount();
    private static final AccountReference CREDITOR_ACCOUNT = AccountReferenceMapperTest.buildAccountReference();
    private static final String CREDITOR_NAME = "creditorName";
    private static final String END_TO_END_IDENTIFICATION = "endToEndIdentification";
    private static final String CREDITOR_AGENT = "creditorAgent";
    private static final Address CREDITOR_ADDRESS = AddressMapperTest.buildAddress();
    private static final String REMITTANCE_INFORMATION_UNSTRUCTURED = "remittanceInformationUnstructured";
    private static final TransactionStatus TRANSACTION_STATUS = TransactionStatus.ACCP;

    @Test
    public void toPaymentInitiationSctWithStatusResponse() {
        SinglePaymentInformationMapper mapper = Mappers.getMapper(SinglePaymentInformationMapper.class);
        PaymentInitiationWithStatusResponseTO response = mapper.toPaymentInitiationSctWithStatusResponse(buildSinglePaymentInitiationInformationWithStatusResponse());

        assertThat(response).isNotNull();
        assertThat(response.getEndToEndIdentification()).isEqualTo(END_TO_END_IDENTIFICATION);

        AccountReferenceTO debtorAccount = response.getDebtorAccount();
        assertThat(debtorAccount).isNotNull();
        assertThat(debtorAccount.getBban()).isEqualTo(DEBTOR_ACCOUNT.getBban());
        assertThat(debtorAccount.getIban()).isEqualTo(DEBTOR_ACCOUNT.getIban());
        assertThat(debtorAccount.getPan()).isEqualTo(DEBTOR_ACCOUNT.getPan());
        assertThat(debtorAccount.getMaskedPan()).isEqualTo(DEBTOR_ACCOUNT.getMaskedPan());
        assertThat(debtorAccount.getMsisdn()).isEqualTo(DEBTOR_ACCOUNT.getMsisdn());
        assertThat(debtorAccount.getCurrency()).isEqualTo(DEBTOR_ACCOUNT.getCurrency());

        AmountTO instructedAmount = response.getInstructedAmount();
        assertThat(instructedAmount).isNotNull();
        assertThat(instructedAmount.getCurrency()).isEqualTo(INSTRUCTED_AMOUNT.getCurrency());
        assertThat(instructedAmount.getAmount()).isEqualTo(INSTRUCTED_AMOUNT.getAmount());

        AccountReferenceTO creditorAccount = response.getCreditorAccount();
        assertThat(creditorAccount).isNotNull();
        assertThat(creditorAccount.getBban()).isEqualTo(CREDITOR_ACCOUNT.getBban());
        assertThat(creditorAccount.getIban()).isEqualTo(CREDITOR_ACCOUNT.getIban());
        assertThat(creditorAccount.getPan()).isEqualTo(CREDITOR_ACCOUNT.getPan());
        assertThat(creditorAccount.getMaskedPan()).isEqualTo(CREDITOR_ACCOUNT.getMaskedPan());
        assertThat(creditorAccount.getMsisdn()).isEqualTo(CREDITOR_ACCOUNT.getMsisdn());
        assertThat(creditorAccount.getCurrency()).isEqualTo(CREDITOR_ACCOUNT.getCurrency());

        assertThat(response.getCreditorAgent()).isEqualTo(CREDITOR_AGENT);
        assertThat(response.getCreditorName()).isEqualTo(CREDITOR_NAME);

        AddressTO creditorAddress = response.getCreditorAddress();
        assertThat(creditorAddress).isNotNull();
        assertThat(creditorAddress.getPostalCode()).isEqualTo(CREDITOR_ADDRESS.getPostalCode());
        assertThat(creditorAddress.getCountry()).isEqualTo(CREDITOR_ADDRESS.getCountry());
        assertThat(creditorAddress.getCity()).isEqualTo(CREDITOR_ADDRESS.getCity());
        assertThat(creditorAddress.getStreet()).isEqualTo(CREDITOR_ADDRESS.getStreet());
        assertThat(creditorAddress.getBuildingNumber()).isEqualTo(CREDITOR_ADDRESS.getBuildingNumber());

        assertThat(response.getRemittanceInformationUnstructured()).isEqualTo(REMITTANCE_INFORMATION_UNSTRUCTURED);

        assertThat(response.getTransactionStatus()).isNotNull();
        assertThat(response.getTransactionStatus().name()).isEqualTo(TRANSACTION_STATUS.name());
    }

    static SinglePaymentInitiationInformationWithStatusResponse buildSinglePaymentInitiationInformationWithStatusResponse() {
        SinglePaymentInitiationInformationWithStatusResponse response = new SinglePaymentInitiationInformationWithStatusResponse();

        response.setDebtorAccount(DEBTOR_ACCOUNT);
        response.setInstructedAmount(INSTRUCTED_AMOUNT);
        response.setCreditorAccount(CREDITOR_ACCOUNT);
        response.setCreditorName(CREDITOR_NAME);
        response.setEndToEndIdentification(END_TO_END_IDENTIFICATION);
        response.setCreditorAgent(CREDITOR_AGENT);
        response.setCreditorAddress(CREDITOR_ADDRESS);
        response.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);
        response.setTransactionStatus(TRANSACTION_STATUS);

        return response;
    }
}
