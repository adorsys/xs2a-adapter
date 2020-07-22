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

    @Mapping(source = "exchangeRate", target = "currencyExchange")
    TransactionDetails toTransactionDetails(FiduciaTransactionDetails value);

    default ReportExchangeRate toReportExchangeRate(FiduciaExchangeRate value) {
        if (value == null) {
            return null;
        }
        ReportExchangeRate target = new ReportExchangeRate();
        target.setContractIdentification(value.getRateContract());
        target.setQuotationDate(value.getRateDate());
        target.setSourceCurrency(value.getCurrencyFrom());
        target.setTargetCurrency(value.getCurrencyTo());
        // XS2AAD-611
        target.setExchangeRate(String.valueOf(Double.parseDouble(value.getRateTo()) / Double.parseDouble(value.getRateFrom())));
        return target;
    }

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
