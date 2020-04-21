package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.service.ing.internal.api.model.*;
import de.adorsys.xs2a.adapter.service.model.Link;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.Address;
import de.adorsys.xs2a.adapter.service.psd2.model.Balance;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import de.adorsys.xs2a.adapter.service.psd2.model.TransactionsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper
public interface IngMapper {
    AccountList map(AccountsResponse value);

    ReadAccountBalanceResponse map(BalancesResponse value);

    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "links", ignore = true)
    TransactionsResponse map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.TransactionsResponse value);

    default String map(UUID value) {
        return value.toString();
    }

    default Map<String, HrefType> map(AccountLinks value) {
        if (value == null) {
            return null;
        }
        HashMap<String, HrefType> links = new HashMap<>();
        if (value.getBalances() != null) {
            links.put("balances", new HrefType(value.getBalances().getHref()));
        }
        if (value.getTransactions() != null) {
            links.put("transactions", new HrefType(value.getTransactions().getHref()));
        }
        return links;
    }

    default String map(TransactionRemittanceInformationStructured value) {
        return value.getReference();
    }

    default Map<String, HrefType> map(LinksNext value) {
        if (value == null) {
            return null;
        }
        if (value.getNext() != null) {
            return Collections.singletonMap("next", new HrefType(value.getNext().getHref()));
        }
        return Collections.emptyMap();
    }

    @Mapping(target = "bban", ignore = true)
    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    AccountReference map(AccountReferenceIban value);

    @Mapping(target = "bban", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cashAccountType", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "bic", ignore = true)
    @Mapping(target = "linkedAccounts", ignore = true)
    @Mapping(target = "usage", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    @Mapping(target = "ownerName", ignore = true)
    AccountDetails map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.Account value);

    @Mapping(target = "creditLimitIncluded", ignore = true)
    @Mapping(target = "lastCommittedTransaction", ignore = true)
    Balance map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.Balance value);

    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    @Mapping(target = "currency", ignore = true)
    AccountReference map(CounterpartyAccount value);

    @Mapping(target = "entryReference", ignore = true)
    @Mapping(target = "mandateId", ignore = true)
    @Mapping(target = "checkId", ignore = true)
    @Mapping(target = "creditorId", ignore = true)
    @Mapping(target = "currencyExchange", ignore = true)
    @Mapping(target = "ultimateCreditor", ignore = true)
    @Mapping(target = "ultimateDebtor", ignore = true)
    @Mapping(target = "additionalInformation", ignore = true)
    @Mapping(target = "purposeCode", ignore = true)
    @Mapping(target = "bankTransactionCode", ignore = true)
    @Mapping(target = "proprietaryBankTransactionCode", ignore = true)
    @Mapping(target = "links", ignore = true)
    @Mapping(target = "creditorAgent", ignore = true)
    @Mapping(target = "debtorAgent", ignore = true)
    @Mapping(target = "remittanceInformationUnstructuredArray", ignore = true)
    @Mapping(target = "remittanceInformationStructuredArray", ignore = true)
    @Mapping(target = "additionalInformationStructured", ignore = true)
    @Mapping(target = "balanceAfterTransaction", ignore = true)
    TransactionDetails map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.Transaction value);

    TokenResponse map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.TokenResponse value);

    Map<String, HrefType> mapToPsd2Links(Map<String, Link> links);

    Map<String, Link> mapToXs2aLinks(Map<String, HrefType> links);

    @Mapping(target = "chargeBearer", ignore = true)
    @Mapping(target = "clearingSystemMemberIdentification", ignore = true)
    @Mapping(target = "debtorName", ignore = true)
    @Mapping(target = "debtorAgent", ignore = true)
    @Mapping(target = "instructionPriority", ignore = true)
    @Mapping(target = "serviceLevelCode", ignore = true)
    @Mapping(target = "localInstrumentCode", ignore = true)
    @Mapping(target = "categoryPurposeCode", ignore = true)
    PaymentInstruction map(PaymentInitiation value);

    @Mapping(target = "streetName", source = "street")
    @Mapping(target = "townName", source = "city")
    @Mapping(target = "postCode", source = "postalCode")
    Address map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.Address value);

    @InheritInverseConfiguration
    de.adorsys.xs2a.adapter.service.ing.internal.api.model.Address map(Address value);

    @Mapping(target = "transactionFees", ignore = true)
    @Mapping(target = "transactionFeeIndicator", ignore = true)
    @Mapping(target = "scaMethods", ignore = true)
    @Mapping(target = "chosenScaMethod", ignore = true)
    @Mapping(target = "challengeData", ignore = true)
    @Mapping(target = "psuMessage", ignore = true)
    PaymentInitiationRequestResponse map(PaymentInitiationResponse value);

    default Map<String, HrefType> map(Links value) {
        if (value == null) {
            return null;
        }
        HashMap<String, HrefType> links = new HashMap<>();
        if (value.getScaRedirect() != null) {
            links.put("scaRedirect", new HrefType(value.getScaRedirect()));
        }
        if (value.getSelf() != null) {
            links.put("self", new HrefType(value.getSelf()));
        }
        if (value.getStatus() != null) {
            links.put("status", new HrefType(value.getStatus()));
        }
        if (value.getDelete() != null) {
            links.put("delete", new HrefType(value.getDelete()));
        }
        return links;
    }

    @Mapping(target = "transactionStatus", ignore = true)
    PaymentInitiationWithStatusResponse map(PaymentInstruction value);

    @Mapping(target = "bban", ignore = true)
    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    AccountReference map(DebtorAccount value);

    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    AccountReference map(CreditorAccount value);

    @Mapping(target = "fundsAvailable", ignore = true)
    @Mapping(target = "psuMessage", ignore = true)
    PaymentInitiationStatusResponse map(PaymentStatusResponse value);

}
