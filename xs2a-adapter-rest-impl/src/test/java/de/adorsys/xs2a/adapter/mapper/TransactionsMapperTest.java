package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.service.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionsMapperTest {
    private static final String TRANSACTION_ID = "transactionId";
    private static final String ENTRY_REFERENCE = "entryReference";
    private static final String END_TO_END_ID = "endToEndId";
    private static final String MANDATE_ID = "mandateId";
    private static final String CHECK_ID = "checkId";
    private static final String CREDITOR_ID = "creditorId";
    private static final LocalDate BOOKING_DATE = LocalDate.now();
    private static final LocalDate VALUE_DATE = LocalDate.now();
    private static final Amount AMOUNT = AmountMapperTest.buildAmount();
    private static final ExchangeRate EXCHANGE_RATE = ExchangeRateMapperTest.buildExchangeRate();
    private static final List<ExchangeRate> EXCHANGE_RATE_LIST = Collections.singletonList(EXCHANGE_RATE);
    private static final String CREDITOR_NAME = "creditorName";
    private static final AccountReference CREDITOR_ACCOUNT = AccountReferenceMapperTest.buildAccountReference();
    private static final String ULTIMATE_CREDITOR = "ultimateCreditor";
    private static final String DEBTOR_NAME = "debtorName";
    private static final AccountReference DEBTOR_ACCOUNT = AccountReferenceMapperTest.buildAccountReference();
    private static final String ULTIMATE_DEBTOR = "ultimateDebtor";
    private static final String REMITTANCE_INFORMATION_UNSTRUCTURED = "remittanceInformationUnstructured";
    private static final RemittanceInformationStructured REMITTANCE_INFORMATION_STRUCTURED =
        buildRemittanceInformationStructured();
    private static final String PROPRIETARY_BANK_TRANSACTION_CODE = "proprietaryBankTransactionCode";
    private static final String SLEB_PURPOSE_CODE = "SLEB";
    private static final PurposeCode PURPOSE_CODE = buildPurposeCode();
    private static final String BANK_TRANSACTION_CODE_STRING = "bankTransactionCode";
    private static final BankTransactionCode BANK_TRANSACTION_CODE = buildBankTransactionCode();
    private static final String LINK_NAME = "linkName";
    private static final String LINK_HREF = "linkHref";
    private static final Link LINK = buildLink();
    private static final Map<String, Link> LINKS_MAP = buildLinksMap();
    private static final String REFERENCE = "reference";
    private static final String REFERENCE_ISSUER = "referenceIssuer";
    private static final String REFERENCE_TYPE = "referenceType";

    private TransactionsMapper transactionsMapper = Mappers.getMapper(TransactionsMapper.class);

    @Test
    public void toTransactionDetails() {
        TransactionDetailsTO transactionDetails = transactionsMapper.toTransactionDetails(buildTransactions());

        assertThat(transactionDetails).isNotNull();
        assertThat(transactionDetails.getTransactionId()).isEqualTo(TRANSACTION_ID);
        assertThat(transactionDetails.getEntryReference()).isEqualTo(ENTRY_REFERENCE);
        assertThat(transactionDetails.getEndToEndId()).isEqualTo(END_TO_END_ID);
        assertThat(transactionDetails.getMandateId()).isEqualTo(MANDATE_ID);
        assertThat(transactionDetails.getCheckId()).isEqualTo(CHECK_ID);
        assertThat(transactionDetails.getCreditorId()).isEqualTo(CREDITOR_ID);
        assertThat(transactionDetails.getBookingDate()).isEqualTo(BOOKING_DATE);
        assertThat(transactionDetails.getValueDate()).isEqualTo(VALUE_DATE);

        AmountTO transactionAmount = transactionDetails.getTransactionAmount();
        assertThat(transactionAmount).isNotNull();
        assertThat(transactionAmount).isEqualToComparingFieldByField(AMOUNT);

        List<ReportExchangeRateTO> exchangeRateList = transactionDetails.getCurrencyExchange();
        assertThat(exchangeRateList).isNotNull();
        assertThat(exchangeRateList.size()).isEqualTo(EXCHANGE_RATE_LIST.size());

        assertThat(transactionDetails.getCreditorName()).isEqualTo(CREDITOR_NAME);

        AccountReferenceTO creditorAccount = transactionDetails.getCreditorAccount();
        assertThat(creditorAccount).isNotNull();
        assertThat(creditorAccount).isEqualToComparingFieldByField(CREDITOR_ACCOUNT);

        assertThat(transactionDetails.getUltimateCreditor()).isEqualTo(ULTIMATE_CREDITOR);
        assertThat(transactionDetails.getDebtorName()).isEqualTo(DEBTOR_NAME);

        AccountReferenceTO debtorAccount = transactionDetails.getDebtorAccount();
        assertThat(debtorAccount).isNotNull();
        assertThat(debtorAccount).isEqualToComparingFieldByField(DEBTOR_ACCOUNT);

        assertThat(transactionDetails.getUltimateDebtor()).isEqualTo(ULTIMATE_DEBTOR);
        assertThat(transactionDetails.getRemittanceInformationUnstructured()).isEqualTo(REMITTANCE_INFORMATION_UNSTRUCTURED);

        RemittanceInformationStructuredTO remittanceInformationStructured =
            transactionDetails.getRemittanceInformationStructured();
        assertThat(remittanceInformationStructured.getReference()).isEqualTo(REFERENCE);
        assertThat(remittanceInformationStructured.getReferenceIssuer()).isEqualTo(REFERENCE_ISSUER);
        assertThat(remittanceInformationStructured.getReferenceType()).isEqualTo(REFERENCE_TYPE);


        PurposeCodeTO purposeCodeTO = transactionDetails.getPurposeCode();
        assertThat(purposeCodeTO).isNotNull();
        assertThat(purposeCodeTO.name()).isEqualTo(PURPOSE_CODE.getCode());

        assertThat(transactionDetails.getBankTransactionCode()).isEqualTo(BANK_TRANSACTION_CODE.getCode());
        assertThat(transactionDetails.getProprietaryBankTransactionCode()).isEqualTo(PROPRIETARY_BANK_TRANSACTION_CODE);

        Map<String, HrefTypeTO> linksMapTO = transactionDetails.getLinks();
        assertThat(linksMapTO).isNotNull();
        assertThat(linksMapTO).hasSameSizeAs(LINKS_MAP);
    }

    @Test
    public void toPurposeCodeTO() {
        PurposeCodeTO purposeCodeTO = transactionsMapper.toPurposeCodeTO(PURPOSE_CODE);

        assertThat(purposeCodeTO).isEqualTo(PurposeCodeTO.SLEB);
    }

    @Test
    public void toBankTransactionCode() {
        String code = transactionsMapper.toBankTransactionCode(BANK_TRANSACTION_CODE);

        assertThat(code).isEqualTo(BANK_TRANSACTION_CODE_STRING);
    }

    static Transactions buildTransactions() {
        Transactions transactions = new Transactions();

        transactions.setTransactionId(TRANSACTION_ID);
        transactions.setEntryReference(ENTRY_REFERENCE);
        transactions.setEndToEndId(END_TO_END_ID);
        transactions.setMandateId(MANDATE_ID);
        transactions.setCheckId(CHECK_ID);
        transactions.setCreditorId(CREDITOR_ID);
        transactions.setBookingDate(BOOKING_DATE);
        transactions.setValueDate(VALUE_DATE);
        transactions.setTransactionAmount(AMOUNT);
        transactions.setExchangeRate(EXCHANGE_RATE_LIST);
        transactions.setCreditorName(CREDITOR_NAME);
        transactions.setCreditorAccount(CREDITOR_ACCOUNT);
        transactions.setUltimateCreditor(ULTIMATE_CREDITOR);
        transactions.setDebtorName(DEBTOR_NAME);
        transactions.setDebtorAccount(DEBTOR_ACCOUNT);
        transactions.setUltimateDebtor(ULTIMATE_DEBTOR);
        transactions.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);
        transactions.setRemittanceInformationStructured(REMITTANCE_INFORMATION_STRUCTURED);
        transactions.setPurposeCode(PURPOSE_CODE);
        transactions.setBankTransactionCode(BANK_TRANSACTION_CODE);
        transactions.setProprietaryBankTransactionCode(PROPRIETARY_BANK_TRANSACTION_CODE);
        transactions.setLinks(LINKS_MAP);

        return transactions;
    }

    private static PurposeCode buildPurposeCode() {
        PurposeCode purposeCode = new PurposeCode();
        purposeCode.setCode(SLEB_PURPOSE_CODE);
        return purposeCode;
    }

    private static BankTransactionCode buildBankTransactionCode() {
        BankTransactionCode bankTransactionCode = new BankTransactionCode();
        bankTransactionCode.setCode(BANK_TRANSACTION_CODE_STRING);
        return bankTransactionCode;
    }

    private static Link buildLink() {
        Link link = new Link();
        link.setHref(LINK_HREF);
        return link;
    }

    private static Map<String, Link> buildLinksMap() {
        Map<String, Link> linksMap = new HashMap<>();
        linksMap.put(LINK_NAME, LINK);
        return linksMap;
    }

    private static RemittanceInformationStructured buildRemittanceInformationStructured() {
        RemittanceInformationStructured remittanceInformationStructured = new RemittanceInformationStructured();
        remittanceInformationStructured.setReference(REFERENCE);
        remittanceInformationStructured.setReferenceIssuer(REFERENCE_ISSUER);
        remittanceInformationStructured.setReferenceType(REFERENCE_TYPE);
        return remittanceInformationStructured;
    }
}
