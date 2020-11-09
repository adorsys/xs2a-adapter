package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.ing.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class IngMapperTest {

    private static final String END_TO_END_IDENTIFICATION = "end-to-end id";
    private static final String DEBTOR_IBAN = "debtor iban";
    private static final String CURRENCY = "currency";
    private static final String AMOUNT = "amount";
    private static final String CREDITOR_IBAN = "creditor iban";
    private static final String CREDITOR_AGENT = "creditor agent";
    private static final String CREDITOR_NAME = "creditor name";
    private static final String STREET = "street";
    private static final String BUILDING_NUMBER = "building number";
    private static final String CITY = "city";
    private static final String POSTAL_CODE = "post code";
    private static final String COUNTRY = "country";
    private static final String REMITTANCE_INFORMATION_UNSTRUCTURED = "remit info unstr";
    private static final LocalDate START_DATE = LocalDate.of(2020, 2, 20);
    private static final LocalDate END_DATE = LocalDate.of(2020, 2, 22);
    private static final OffsetDateTime LAST_CHANGE_DATE_TIME = OffsetDateTime.of(END_DATE, LocalTime.of(20, 20), ZoneOffset.UTC);
    private static final String PAYMENT_ID = "payment id";
    private static final String XML = "xml";
    private static final String TRANSACTION_ID = "transaction id";
    private static final String END_TO_END_ID = "end-to-end id";
    private static final String DEBTOR_NAME = "debtor name";
    private static final String REFERENCE = "reference";
    private static final String REFERENCE_TYPE = "reference type";
    private static final String REFERENCE_ISSUER = "reference issuer";
    private static final UUID RESOURCE_ID = UUID.randomUUID();
    private static final String PRODUCT = "product";
    private final IngMapper mapper = Mappers.getMapper(IngMapper.class);

    @Test
    void mapPeriodicPaymentInitiationJson() {
        PeriodicPaymentInitiationJson value = new PeriodicPaymentInitiationJson();
        value.setEndToEndIdentification(END_TO_END_IDENTIFICATION);
        value.setDebtorAccount(debtorAccount());
        value.setInstructedAmount(amount());
        value.setCreditorAccount(creditorAccount());
        value.setCreditorAgent(CREDITOR_AGENT);
        value.setCreditorName(CREDITOR_NAME);
        value.setCreditorAddress(creditorAddress());
        value.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);
        value.setStartDate(START_DATE);
        value.setEndDate(END_DATE);
        value.setExecutionRule(ExecutionRule.FOLLOWING);
        value.setFrequency(FrequencyCode.DAILY);
        value.setDayOfExecution(DayOfExecution._1);

        IngPeriodicPaymentInitiationJson mapped = mapper.map(value);

        assertThat(mapped.getEndToEndIdentification()).isEqualTo(END_TO_END_IDENTIFICATION);
        assertThat(mapped.getDebtorAccount().getIban()).isEqualTo(DEBTOR_IBAN);
        assertThat(mapped.getDebtorAccount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getInstructedAmount().getAmount()).isEqualTo(AMOUNT);
        assertThat(mapped.getInstructedAmount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getCreditorAccount().getIban()).isEqualTo(CREDITOR_IBAN);
        assertThat(mapped.getCreditorAccount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getCreditorAgent()).isEqualTo(CREDITOR_AGENT);
        assertThat(mapped.getCreditorName()).isEqualTo(CREDITOR_NAME);
        assertThat(mapped.getCreditorAddress().getStreet()).isEqualTo(STREET);
        assertThat(mapped.getCreditorAddress().getBuildingNumber()).isEqualTo(BUILDING_NUMBER);
        assertThat(mapped.getCreditorAddress().getCity()).isEqualTo(CITY);
        assertThat(mapped.getCreditorAddress().getPostalCode()).isEqualTo(POSTAL_CODE);
        assertThat(mapped.getCreditorAddress().getCountry()).isEqualTo(COUNTRY);
        assertThat(mapped.getRemittanceInformationUnstructured()).isEqualTo(REMITTANCE_INFORMATION_UNSTRUCTURED);
        assertThat(mapped.getStartDate()).isEqualTo(START_DATE);
        assertThat(mapped.getEndDate()).isEqualTo(END_DATE);
        assertThat(mapped.getFrequency()).isEqualTo(IngFrequencyCode.DAIL);
        assertThat(mapped.getDayOfExecution()).isEqualTo(IngDayOfExecution._1);
    }

    private AccountReference debtorAccount() {
        AccountReference debtorAccount = new AccountReference();
        debtorAccount.setIban(DEBTOR_IBAN);
        debtorAccount.setCurrency(CURRENCY);
        return debtorAccount;
    }

    private Amount amount() {
        Amount instructedAmount = new Amount();
        instructedAmount.setAmount(AMOUNT);
        instructedAmount.setCurrency(CURRENCY);
        return instructedAmount;
    }

    private AccountReference creditorAccount() {
        AccountReference creditorAccount = new AccountReference();
        creditorAccount.setIban(CREDITOR_IBAN);
        creditorAccount.setCurrency(CURRENCY);
        return creditorAccount;
    }

    private Address creditorAddress() {
        Address creditorAddress = new Address();
        creditorAddress.setStreetName(STREET);
        creditorAddress.setBuildingNumber(BUILDING_NUMBER);
        creditorAddress.setTownName(CITY);
        creditorAddress.setPostCode(POSTAL_CODE);
        creditorAddress.setCountry(COUNTRY);
        return creditorAddress;
    }

    @Test
    void mapIngPeriodicPaymentInitiationJson() {
        IngPeriodicPaymentInitiationJson value = new IngPeriodicPaymentInitiationJson();
        value.setEndToEndIdentification(END_TO_END_IDENTIFICATION);
        value.setDebtorAccount(ingDebtorAccount());
        value.setInstructedAmount(ingAmount());
        value.setCreditorAccount(ingCreditorAccount());
        value.setCreditorAgent(CREDITOR_AGENT);
        value.setCreditorName(CREDITOR_NAME);
        value.setCreditorAddress(ingCreditorAddress());
        value.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);
        value.setStartDate(START_DATE);
        value.setEndDate(END_DATE);
        value.setFrequency(IngFrequencyCode.MNTH);
        value.setDayOfExecution(IngDayOfExecution._2);

        PeriodicPaymentInitiationWithStatusResponse mapped = mapper.map(value);

        assertThat(mapped.getEndToEndIdentification()).isEqualTo(END_TO_END_IDENTIFICATION);
        assertThat(mapped.getDebtorAccount().getIban()).isEqualTo(DEBTOR_IBAN);
        assertThat(mapped.getDebtorAccount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getInstructedAmount().getAmount()).isEqualTo(AMOUNT);
        assertThat(mapped.getInstructedAmount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getCreditorAccount().getIban()).isEqualTo(CREDITOR_IBAN);
        assertThat(mapped.getCreditorAccount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getCreditorAgent()).isEqualTo(CREDITOR_AGENT);
        assertThat(mapped.getCreditorName()).isEqualTo(CREDITOR_NAME);
        assertThat(mapped.getCreditorAddress()).isEqualTo(creditorAddress());
        assertThat(mapped.getRemittanceInformationUnstructured()).isEqualTo(REMITTANCE_INFORMATION_UNSTRUCTURED);
        assertThat(mapped.getStartDate()).isEqualTo(START_DATE);
        assertThat(mapped.getEndDate()).isEqualTo(END_DATE);
        assertThat(mapped.getFrequency()).isEqualTo(FrequencyCode.MONTHLY);
        assertThat(mapped.getDayOfExecution()).isEqualTo(DayOfExecution._2);
    }

    private IngDebtorAccount ingDebtorAccount() {
        IngDebtorAccount ingDebtorAccount = new IngDebtorAccount();
        ingDebtorAccount.setCurrency(CURRENCY);
        ingDebtorAccount.setIban(DEBTOR_IBAN);
        return ingDebtorAccount;
    }

    private IngAmount ingAmount() {
        IngAmount ingInstructedAmount = new IngAmount();
        ingInstructedAmount.setAmount(AMOUNT);
        ingInstructedAmount.setCurrency(CURRENCY);
        return ingInstructedAmount;
    }

    private IngCreditorAccount ingCreditorAccount() {
        IngCreditorAccount ingCreditorAccount = new IngCreditorAccount();
        ingCreditorAccount.setIban(CREDITOR_IBAN);
        ingCreditorAccount.setCurrency(CURRENCY);
        return ingCreditorAccount;
    }

    private IngAddress ingCreditorAddress() {
        IngAddress ingCreditorAddress = new IngAddress();
        ingCreditorAddress.setBuildingNumber(BUILDING_NUMBER);
        ingCreditorAddress.setCity(CITY);
        ingCreditorAddress.setStreet(STREET);
        ingCreditorAddress.setCountry(COUNTRY);
        ingCreditorAddress.setPostalCode(POSTAL_CODE);
        return ingCreditorAddress;
    }

    @Test
    void mapIngPeriodicPaymentInitiationResponse() {
        IngPeriodicPaymentInitiationResponse value = new IngPeriodicPaymentInitiationResponse();
        value.setTransactionStatus("RCVD");
        value.setPaymentId(PAYMENT_ID);
        value.setLinks(ingPeriodicLinks());

        PaymentInitationRequestResponse201 mapped = mapper.map(value);

        assertThat(mapped.getPaymentId()).isEqualTo(PAYMENT_ID);
        assertThat(mapped.getTransactionStatus()).isEqualTo(TransactionStatus.RCVD);
        assertThat(mapped.getLinks().get("self").getHref()).isEqualTo("self href");
        assertThat(mapped.getLinks().get("status").getHref()).isEqualTo("status href");
        assertThat(mapped.getLinks().get("scaRedirect").getHref()).isEqualTo("sca redirect href");
        assertThat(mapped.getLinks().get("delete").getHref()).isEqualTo("delete href");
    }

    private IngPeriodicLinks ingPeriodicLinks() {
        IngPeriodicLinks ingPeriodicLinks = new IngPeriodicLinks();
        ingPeriodicLinks.setSelf("self href");
        ingPeriodicLinks.setStatus("status href");
        ingPeriodicLinks.setScaRedirect("sca redirect href");
        ingPeriodicLinks.setDelete("delete href");
        return ingPeriodicLinks;
    }

    @Test
    void mapPeriodicPaymentInitiationMultipartBody() {
        PeriodicPaymentInitiationMultipartBody value = new PeriodicPaymentInitiationMultipartBody();
        value.setXml_sct(XML);
        value.setJson_standingorderType(jsonStandingOrderType());

        IngPeriodicPaymentInitiationXml mapped = mapper.map(value);

        assertThat(mapped.getXml_sct()).isEqualTo(XML);
        assertThat(mapped.getStartDate()).isEqualTo(START_DATE);
        assertThat(mapped.getEndDate()).isEqualTo(END_DATE);
        assertThat(mapped.getFrequency()).isEqualTo(IngFrequencyCode.WEEK);
        assertThat(mapped.getDayOfExecution()).isEqualTo(IngDayOfExecution._3);
    }

    private PeriodicPaymentInitiationXmlPart2StandingorderTypeJson jsonStandingOrderType() {
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson jsonStandingOrderType =
            new PeriodicPaymentInitiationXmlPart2StandingorderTypeJson();
        jsonStandingOrderType.setStartDate(START_DATE);
        jsonStandingOrderType.setEndDate(END_DATE);
        jsonStandingOrderType.setFrequency(FrequencyCode.WEEKLY);
        jsonStandingOrderType.setDayOfExecution(DayOfExecution._3);
        return jsonStandingOrderType;
    }

    @Test
    void mapIngPeriodicPaymentInitiationXml() {
        IngPeriodicPaymentInitiationXml value = new IngPeriodicPaymentInitiationXml();
        value.setXml_sct(XML);
        value.setStartDate(START_DATE);
        value.setEndDate(END_DATE);
        value.setFrequency(IngFrequencyCode.WEEK);
        value.setDayOfExecution(IngDayOfExecution._3);

        PeriodicPaymentInitiationMultipartBody mapped = mapper.map(value);

        assertThat(mapped.getXml_sct()).isEqualTo(XML);
        assertThat(mapped.getJson_standingorderType()).isEqualTo(jsonStandingOrderType());
    }

    @Test
    void mapIngPaymentAgreementStatusResponse() {
        IngPaymentAgreementStatusResponse value = new IngPaymentAgreementStatusResponse();
        value.setTransactionStatus(IngPaymentAgreementStatusResponse.TransactionStatus.RCVD);

        PaymentInitiationStatusResponse200Json mapped = mapper.map(value);

        assertThat(mapped.getTransactionStatus()).isEqualTo(TransactionStatus.RCVD);
    }

    @Test
    void mapIngPaymentStatusResponse() {
        IngPaymentStatusResponse value = new IngPaymentStatusResponse();
        value.setTransactionStatus("RCVD");

        PaymentInitiationStatusResponse200Json mapped = mapper.map(value);

        assertThat(mapped.getTransactionStatus()).isEqualTo(TransactionStatus.RCVD);
    }

    @Test
    void mapIngPaymentInstruction() {
        IngPaymentInstruction value = new IngPaymentInstruction();
        value.setEndToEndIdentification(END_TO_END_IDENTIFICATION);
        value.setDebtorAccount(ingDebtorAccount());
        value.setInstructedAmount(ingAmount());
        value.setCreditorAccount(ingCreditorAccount());
        value.setCreditorName(CREDITOR_NAME);
        value.setCreditorAddress(ingCreditorAddress());
        value.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);

        PaymentInitiationWithStatusResponse mapped = mapper.map(value);

        assertThat(mapped.getEndToEndIdentification()).isEqualTo(END_TO_END_IDENTIFICATION);
        assertThat(mapped.getDebtorAccount()).isEqualTo(debtorAccount());
        assertThat(mapped.getInstructedAmount()).isEqualTo(amount());
        assertThat(mapped.getCreditorAccount()).isEqualTo(creditorAccount());
        assertThat(mapped.getCreditorName()).isEqualTo(CREDITOR_NAME);
        assertThat(mapped.getCreditorAddress()).isEqualTo(creditorAddress());
        assertThat(mapped.getRemittanceInformationUnstructured()).isEqualTo(REMITTANCE_INFORMATION_UNSTRUCTURED);
    }

    @Test
    void mapIngPaymentInitiationResponse() {
        IngPaymentInitiationResponse value = new IngPaymentInitiationResponse();
        value.setTransactionStatus("RCVD");
        value.setPaymentId(PAYMENT_ID);
        value.setLinks(ingLinks());
        value.setTppMessages(Collections.singletonList(ingTppMessage()));

        PaymentInitationRequestResponse201 mapped = mapper.map(value);

        assertThat(mapped.getTransactionStatus()).isEqualTo(TransactionStatus.RCVD);
        assertThat(mapped.getPaymentId()).isEqualTo(PAYMENT_ID);
        assertThat(mapped.getLinks().get("delete").getHref()).isEqualTo("delete href");
        assertThat(mapped.getLinks().get("scaRedirect").getHref()).isEqualTo("sca redirect href");
        assertThat(mapped.getLinks().get("self").getHref()).isEqualTo("self href");
        assertThat(mapped.getLinks().get("status").getHref()).isEqualTo("status href");
        assertThat(mapped.getTppMessages().get(0)).isEqualTo(tppMessage());
    }

    private IngLinks ingLinks() {
        IngLinks ingLinks = new IngLinks();
        ingLinks.setDelete("delete href");
        ingLinks.setScaRedirect("sca redirect href");
        ingLinks.setSelf("self href");
        ingLinks.setStatus("status href");
        return ingLinks;
    }

    private IngTppMessage ingTppMessage() {
        IngTppMessage ingTppMessage = new IngTppMessage();
        ingTppMessage.setCategory("WARNING");
        ingTppMessage.setCode("WARNING");
        ingTppMessage.setPath("path");
        ingTppMessage.setText("text");
        return ingTppMessage;
    }

    private TppMessage2XX tppMessage() {
        TppMessage2XX tppMessage = new TppMessage2XX();
        tppMessage.setCategory(TppMessageCategory.WARNING);
        tppMessage.setCode(MessageCode2XX.WARNING);
        tppMessage.setPath("path");
        tppMessage.setText("text");
        return tppMessage;
    }

    @Test
    void mapPaymentInitiationJson() {
        PaymentInitiationJson value = new PaymentInitiationJson();
        value.setEndToEndIdentification(END_TO_END_IDENTIFICATION);
        value.setDebtorAccount(debtorAccount());
        value.setInstructedAmount(amount());
        value.setCreditorAccount(creditorAccount());
        value.setCreditorName(CREDITOR_NAME);
        value.setCreditorAddress(creditorAddress());
        value.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);

        IngPaymentInstruction mapped = mapper.map(value);

        assertThat(mapped.getEndToEndIdentification()).isEqualTo(END_TO_END_IDENTIFICATION);
        assertThat(mapped.getDebtorAccount().getIban()).isEqualTo(DEBTOR_IBAN);
        assertThat(mapped.getDebtorAccount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getInstructedAmount().getAmount()).isEqualTo(AMOUNT);
        assertThat(mapped.getInstructedAmount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getCreditorAccount().getIban()).isEqualTo(CREDITOR_IBAN);
        assertThat(mapped.getCreditorAccount().getCurrency()).isEqualTo(CURRENCY);
        assertThat(mapped.getCreditorName()).isEqualTo(CREDITOR_NAME);
        assertThat(mapped.getCreditorAddress().getCountry()).isEqualTo(COUNTRY);
        assertThat(mapped.getCreditorAddress().getCity()).isEqualTo(CITY);
        assertThat(mapped.getCreditorAddress().getPostalCode()).isEqualTo(POSTAL_CODE);
        assertThat(mapped.getCreditorAddress().getStreet()).isEqualTo(STREET);
        assertThat(mapped.getCreditorAddress().getBuildingNumber()).isEqualTo(BUILDING_NUMBER);
        assertThat(mapped.getRemittanceInformationUnstructured()).isEqualTo(REMITTANCE_INFORMATION_UNSTRUCTURED);
    }

    @Test
    void mapIngTransactionsResponse() {
        IngTransactionsResponse value = new IngTransactionsResponse();
        value.setAccount(ingAccountReference());
        value.setTransactions(ingTransactions());

        TransactionsResponse200Json mapped = mapper.map(value);

        assertThat(mapped.getAccount()).isEqualTo(creditorAccount());
        assertThat(mapped.getTransactions()).isEqualTo(transactions());
    }

    private IngAccountReferenceIban ingAccountReference() {
        IngAccountReferenceIban ingAccount = new IngAccountReferenceIban();
        ingAccount.setIban(CREDITOR_IBAN);
        ingAccount.setCurrency(CURRENCY);
        return ingAccount;
    }

    private IngTransactions ingTransactions() {
        IngTransactions ingTransactions = new IngTransactions();
        ingTransactions.setBooked(Collections.singletonList(ingTransaction()));
        ingTransactions.setPending(Collections.singletonList(ingTransaction()));
        ingTransactions.setLinks(ingLinksNext());
        return ingTransactions;
    }

    private IngTransaction ingTransaction() {
        IngTransaction ingTransaction = new IngTransaction();
        ingTransaction.setTransactionId(TRANSACTION_ID);
        ingTransaction.setEndToEndId(END_TO_END_ID);
        ingTransaction.setBookingDate(START_DATE);
        ingTransaction.setValueDate(END_DATE);
        ingTransaction.setTransactionAmount(ingAmount());
        ingTransaction.setCreditorName(CREDITOR_NAME);
        ingTransaction.setCreditorAccount(ingCreditorCounterpartyAccount());
        ingTransaction.setDebtorName(DEBTOR_NAME);
        ingTransaction.setDebtorAccount(ingDebtorCounterpartyAccount());
        ingTransaction.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);
        ingTransaction.setRemittanceInformationStructured(ingRemittanceInformationStructured());
        return ingTransaction;
    }

    private IngCounterpartyAccount ingCreditorCounterpartyAccount() {
        IngCounterpartyAccount ingCreditorCounterpartyAccount = new IngCounterpartyAccount();
        ingCreditorCounterpartyAccount.setIban(CREDITOR_IBAN);
        return ingCreditorCounterpartyAccount;
    }

    private IngCounterpartyAccount ingDebtorCounterpartyAccount() {
        IngCounterpartyAccount ingDebtorCounterpartyAccount = new IngCounterpartyAccount();
        ingDebtorCounterpartyAccount.setIban(DEBTOR_IBAN);
        return ingDebtorCounterpartyAccount;
    }

    private IngTransactionRemittanceInformationStructured ingRemittanceInformationStructured() {
        IngTransactionRemittanceInformationStructured ingRemittanceInformationStructured =
            new IngTransactionRemittanceInformationStructured();
        ingRemittanceInformationStructured.setReference(REFERENCE);
        ingRemittanceInformationStructured.setReferenceIssuer(REFERENCE_ISSUER);
        ingRemittanceInformationStructured.setReferenceType(REFERENCE_TYPE);
        return ingRemittanceInformationStructured;
    }

    private IngLinksNext ingLinksNext() {
        IngLinksNext ingLinksNext = new IngLinksNext();
        ingLinksNext.setNext(ingHrefType("next href"));
        return ingLinksNext;
    }

    private IngHrefType ingHrefType(String href) {
        IngHrefType ingHrefType = new IngHrefType();
        ingHrefType.setHref(href);
        return ingHrefType;
    }

    private AccountReport transactions() {
        AccountReport transaction = new AccountReport();
        transaction.setBooked(Collections.singletonList(transaction()));
        transaction.setPending(Collections.singletonList(transaction()));
        transaction.setLinks(Collections.singletonMap("next", hrefType("next href")));
        return transaction;
    }

    private HrefType hrefType(String href) {
        HrefType hrefType = new HrefType();
        hrefType.setHref(href);
        return hrefType;
    }

    private TransactionDetails transaction() {
        TransactionDetails transaction = new TransactionDetails();
        transaction.setTransactionId(TRANSACTION_ID);
        transaction.setEndToEndId(END_TO_END_ID);
        transaction.setBookingDate(START_DATE);
        transaction.setValueDate(END_DATE);
        transaction.setTransactionAmount(amount());
        transaction.setCreditorName(CREDITOR_NAME);
        transaction.setCreditorAccount(transactionCreditorAccount());
        transaction.setDebtorName(DEBTOR_NAME);
        transaction.setDebtorAccount(transactionDebtorAccount());
        transaction.setRemittanceInformationUnstructured(REMITTANCE_INFORMATION_UNSTRUCTURED);
        transaction.setRemittanceInformationStructured(REFERENCE);
        return transaction;
    }

    private AccountReference transactionCreditorAccount() {
        AccountReference transactionCreditorAccount = new AccountReference();
        transactionCreditorAccount.setIban(CREDITOR_IBAN);
        return transactionCreditorAccount;
    }

    private AccountReference transactionDebtorAccount() {
        AccountReference transactionDebtorAccount = new AccountReference();
        transactionDebtorAccount.setIban(DEBTOR_IBAN);
        return transactionDebtorAccount;
    }

    private RemittanceInformationStructured remittanceInformationStructured() {
        RemittanceInformationStructured remittanceInformationStructured = new RemittanceInformationStructured();
        remittanceInformationStructured.setReference(REFERENCE);
        remittanceInformationStructured.setReferenceType(REFERENCE_TYPE);
        remittanceInformationStructured.setReferenceIssuer(REFERENCE_ISSUER);
        return remittanceInformationStructured;
    }

    @Test
    void mapIngBalancesResponse() {
        IngBalancesResponse value = new IngBalancesResponse();
        value.setAccount(ingAccountReference());
        value.setBalances(Collections.singletonList(ingBalance()));

        ReadAccountBalanceResponse200 mapped = mapper.map(value);

        assertThat(mapped.getAccount()).isEqualTo(creditorAccount());
        assertThat(mapped.getBalances()).containsExactly(balance());
    }

    private IngBalance ingBalance() {
        IngBalance ingBalance = new IngBalance();
        ingBalance.setBalanceType("closingBooked");
        ingBalance.setBalanceAmount(ingAmount());
        ingBalance.setLastChangeDateTime(LAST_CHANGE_DATE_TIME);
        ingBalance.setReferenceDate(END_DATE);
        return ingBalance;
    }

    private Balance balance() {
        Balance balance = new Balance();
        balance.setBalanceType(BalanceType.CLOSINGBOOKED);
        balance.setBalanceAmount(amount());
        balance.setLastChangeDateTime(LAST_CHANGE_DATE_TIME);
        balance.setReferenceDate(END_DATE);
        return balance;
    }

    @Test
    void mapIngAccountsResponse() {
        IngAccountsResponse value = new IngAccountsResponse();
        value.setAccounts(Collections.singletonList(ingAccount()));

        AccountList mapped = mapper.map(value);

        assertThat(mapped.getAccounts()).containsExactly(account());
    }

    private IngAccount ingAccount() {
        IngAccount ingAccount = new IngAccount();
        ingAccount.setResourceId(RESOURCE_ID);
        ingAccount.setIban(CREDITOR_IBAN);
        ingAccount.setName(CREDITOR_NAME);
        ingAccount.setCurrency(CURRENCY);
        ingAccount.setProduct(PRODUCT);
        ingAccount.setLinks(ingAccountLinks());
        return ingAccount;
    }

    private IngAccountLinks ingAccountLinks() {
        IngAccountLinks ingAccountLinks = new IngAccountLinks();
        ingAccountLinks.setBalances(ingHrefType("balances href"));
        ingAccountLinks.setTransactions(ingHrefType("transactions href"));
        return ingAccountLinks;
    }

    private AccountDetails account() {
        AccountDetails account = new AccountDetails();
        account.setResourceId(RESOURCE_ID.toString());
        account.setIban(CREDITOR_IBAN);
        account.setName(CREDITOR_NAME);
        account.setCurrency(CURRENCY);
        account.setProduct(PRODUCT);
        Map<String, HrefType> links = new HashMap<>();
        links.put("balances", hrefType("balances href"));
        links.put("transactions", hrefType("transactions href"));
        account.setLinks(links);
        return account;
    }

    @Test
    void toTransactionStatus() {
        assertAll(
            () -> assertThat(mapper.toTransactionStatus("ACTV")).isEqualTo(TransactionStatus.ACSP),
            () -> assertThat(mapper.toTransactionStatus("EXPI")).isEqualTo(TransactionStatus.ACSC)
        );
    }
}
