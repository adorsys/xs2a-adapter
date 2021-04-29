package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.sparkasse.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

@Mapper
public interface SparkasseMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(SparkasseTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(SparkasseOK200TransactionDetails value);
    Transactions toTransactions(SparkasseTransactionDetails value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactions(value))")
    TransactionDetailsBody toTransactionDetailsBody(SparkasseTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }

    @ValueMapping(target = "PUSH_OTP", source = "PUSH_DEC")
    AuthenticationType toAuthenticationType(SparkasseAuthenticationType value);

    ConsentsResponse201 toConsentsResponse201(SparkasseConsentsResponse201 value);
    PaymentInitationRequestResponse201 toPaymentInitationRequestResponse201(SparkassePaymentInitationRequestResponse201 value);
    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(SparkasseSelectPsuAuthenticationMethodResponse value);
    StartScaprocessResponse toStartScaprocessResponse(SparkasseStartScaprocessResponse value);
    UpdatePsuAuthenticationResponse toUpdatePsuAuthenticationResponse(SparkasseUpdatePsuAuthenticationResponse value);
}
