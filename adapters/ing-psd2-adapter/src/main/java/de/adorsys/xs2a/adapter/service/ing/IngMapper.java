package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.service.ing.internal.api.model.*;
import de.adorsys.xs2a.adapter.service.model.Link;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.Balance;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import de.adorsys.xs2a.adapter.service.psd2.model.TransactionsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
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
    TransactionDetails map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.Transaction value);

    TokenResponse map(de.adorsys.xs2a.adapter.service.ing.internal.api.model.TokenResponse value);

    Map<String, HrefType> mapToPsd2Links(Map<String, Link> links);

    Map<String, Link> mapToXs2aLinks(Map<String, HrefType> links);
}
