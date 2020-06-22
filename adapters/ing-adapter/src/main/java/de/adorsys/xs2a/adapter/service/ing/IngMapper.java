package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.api.model.Address;
import de.adorsys.xs2a.adapter.api.model.Balance;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.*;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
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

    ReadAccountBalanceResponse200 map(BalancesResponse value);

    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "links", ignore = true)
    TransactionsResponse200Json map(TransactionsResponse value);

    default String map(UUID value) {
        return value.toString();
    }

    default Map<String, HrefType> map(AccountLinks value) {
        if (value == null) {
            return null;
        }
        HashMap<String, HrefType> links = new HashMap<>();
        if (value.getBalances() != null) {
            HrefType balances = new HrefType();
            balances.setHref(value.getBalances().getHref());
            links.put("balances", balances);
        }
        if (value.getTransactions() != null) {
            HrefType transactions = new HrefType();
            transactions.setHref(value.getTransactions().getHref());
            links.put("transactions", transactions);
        }
        return links;
    }

    default Map<String, HrefType> map(LinksNext value) {
        if (value == null) {
            return null;
        }
        if (value.getNext() != null) {
            HrefType next = new HrefType();
            next.setHref(value.getNext().getHref());
            return Collections.singletonMap("next", next);
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
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    @Mapping(target = "ownerName", ignore = true)
    AccountDetails map(Account value);

    Balance map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.Balance value);

    default BalanceType toBalanceType(String value) {
        return BalanceType.fromValue(value);
    }

    default TransactionStatus toTransactionStatus(String value) {
        return TransactionStatus.fromValue(value);
    }

    default TppMessageCategory toTppMessageCategory(String value) {
        return TppMessageCategory.fromValue(value);
    }

    default MessageCode2XX toMessageCode2XX(String value) {
        return MessageCode2XX.fromValue(value);
    }

    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    @Mapping(target = "currency", ignore = true)
    AccountReference map(CounterpartyAccount value);

    @Mapping(target = "entryReference", ignore = true)
    @Mapping(target = "mandateId", ignore = true)
    @Mapping(target = "checkId", ignore = true)
    @Mapping(target = "creditorId", ignore = true)
    @Mapping(target = "ultimateCreditor", ignore = true)
    @Mapping(target = "ultimateDebtor", ignore = true)
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
    TransactionDetails map(Transaction value);

    TokenResponse map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.TokenResponse value);

    @Mapping(target = "chargeBearer", ignore = true)
    @Mapping(target = "clearingSystemMemberIdentification", ignore = true)
    @Mapping(target = "debtorName", ignore = true)
    @Mapping(target = "debtorAgent", ignore = true)
    @Mapping(target = "instructionPriority", ignore = true)
    @Mapping(target = "serviceLevelCode", ignore = true)
    @Mapping(target = "localInstrumentCode", ignore = true)
    @Mapping(target = "categoryPurposeCode", ignore = true)
    PaymentInstruction map(PaymentInitiationJson value);

    @Mapping(target = "streetName", source = "street")
    @Mapping(target = "townName", source = "city")
    @Mapping(target = "postCode", source = "postalCode")
    Address map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.Address value);

    @InheritInverseConfiguration
    de.adorsys.xs2a.adapter.service.ing.internal.api.model.Address map(Address value);

    @Mapping(target = "transactionFees", ignore = true)
    @Mapping(target = "transactionFeeIndicator", ignore = true)
    @Mapping(target = "scaMethods", ignore = true)
    @Mapping(target = "challengeData", ignore = true)
    @Mapping(target = "psuMessage", ignore = true)
    PaymentInitationRequestResponse201 map(PaymentInitiationResponse value);

    default Map<String, HrefType> map(Links value) {
        if (value == null) {
            return null;
        }
        HashMap<String, HrefType> links = new HashMap<>();
        if (value.getScaRedirect() != null) {
            HrefType scaRedirect = new HrefType();
            scaRedirect.setHref(value.getScaRedirect());
            links.put("scaRedirect", scaRedirect);
        }
        if (value.getSelf() != null) {
            HrefType self = new HrefType();
            self.setHref(value.getSelf());
            links.put("self", self);
        }
        if (value.getStatus() != null) {
            HrefType status = new HrefType();
            status.setHref(value.getStatus());
            links.put("status", status);
        }
        if (value.getDelete() != null) {
            HrefType delete = new HrefType();
            delete.setHref(value.getDelete());
            links.put("delete", delete);
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

    PaymentInitiationStatusResponse200Json map(PaymentStatusResponse value);

}
