package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditOK200TransactionDetails;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditTransactionDetails;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditTransactionResponse200Json;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditUpdatePsuAuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface UnicreditMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(UnicreditTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(UnicreditOK200TransactionDetails value);
    Transactions toTransactions(UnicreditTransactionDetails value);
    UpdatePsuAuthenticationResponse toUpdatePsuAuthenticationResponse(UnicreditUpdatePsuAuthenticationResponse value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(UnicreditTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }

    default Map<String, HrefType> map(Collection<Map<String, HrefType>> value) {
        return value == null ? null : value.stream().findFirst().orElse(new HashMap<>());
    }
}
