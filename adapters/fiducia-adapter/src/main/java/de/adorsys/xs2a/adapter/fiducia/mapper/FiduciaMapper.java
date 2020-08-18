package de.adorsys.xs2a.adapter.fiducia.mapper;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.fiducia.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper
public interface FiduciaMapper {
    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(
        FiduciaSelectPsuAuthenticationMethodResponse value);

    default List<String> toListOfStrings(String challengeData) {
        if (challengeData == null) {
            return null;
        }
        return Collections.singletonList(challengeData);
    }

    StartScaprocessResponse toStartScaProcessResponse(FiduciaStartScaProcessResponse value);

    UpdatePsuAuthenticationResponse toUpdatePsuAuthenticationResponse(FiduciaUpdatePsuAuthenticationResponse value);

    FiduciaPeriodicPaymentInitiationJson toFiduciaPeriodicPaymentInitiationJson(PeriodicPaymentInitiationJson value);

    PeriodicPaymentInitiationWithStatusResponse toPeriodicPaymentInitiationWithStatusResponse(
        FiduciaPeriodicPaymentInitiationWithStatusResponse value);

    PeriodicPaymentInitiationMultipartBody toPeriodicPaymentInitiationMultipartBody(
        FiduciaPeriodicPaymentInitiationMultipartBody value);

    TransactionsResponse200Json toTransactionsResponse200Json(FiduciaTransactionsResponse200Json value);

    OK200TransactionDetails toOK200TransactionDetails(FiduciaOK200TransactionDetails value);

    @Mapping(target = "currencyExchange", ignore = true)
    TransactionDetails toTransactionDetails(FiduciaTransactionDetails value);

    default RemittanceInformationStructured toRemittanceInformationStructured(String value) {
        if (value == null) {
            return null;
        }

        RemittanceInformationStructured remittanceInformationStructured = new RemittanceInformationStructured();
        remittanceInformationStructured.setReference(value);
        return remittanceInformationStructured;
    }

    PaymentInitationRequestResponse201 toPaymentInitationRequestResponse201(FiduciaPaymentInitationRequestResponse201 value);
}
