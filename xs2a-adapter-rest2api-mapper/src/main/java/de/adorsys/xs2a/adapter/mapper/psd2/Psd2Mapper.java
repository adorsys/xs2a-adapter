package de.adorsys.xs2a.adapter.mapper.psd2;

import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface Psd2Mapper {
    ConsentsResponseTO toConsentsResponseTO(ConsentsResponse consentsResponse);

    ConsentsResponse toConsentsResponse(ConsentsResponseTO consentsResponse);

    Consents toConsents(ConsentsTO consents);

    ConsentsTO toConsentsTO(Consents consents);

    ConsentInformationResponseTO toConsentInformationResponseTO(ConsentInformationResponse consentInformationResponse);

    ConsentInformationResponse toConsentInformationResponse(ConsentInformationResponseTO consentInformationResponse);

    ConsentStatusResponseTO toConsentStatusResponseTO(ConsentStatusResponse consentStatusResponse);

    ConsentStatusResponse toConsentStatusResponse(ConsentStatusResponseTO consentStatusResponse);

    ScaStatusResponseTO toScaStatusResponseTO(ScaStatusResponse consentScaStatus);

    ScaStatusResponse toScaStatusResponse(ScaStatusResponseTO consentScaStatus);

    AccountListTO toAccountListTO(AccountList accountList);

    AccountList toAccountList(AccountListTO accountList);

    ReadAccountBalanceResponseTO toReadAccountBalanceResponseTO(ReadAccountBalanceResponse balances);

    ReadAccountBalanceResponse toReadAccountBalanceResponse(ReadAccountBalanceResponseTO readAccountBalanceResponse);

    TransactionsResponseTO toTransactionsResponseTO(TransactionsResponse transactions);

    TransactionsResponse toTransactionsResponse(TransactionsResponseTO transactions);

    UpdateAuthorisation toUpdateAuthorisation(UpdateAuthorisationTO updateAuthorisation);

    UpdateAuthorisationTO toUpdateAuthorisationTO(UpdateAuthorisation updateAuthorisation);

    StartScaProcessResponseTO toStartScaProcessResponseTO(StartScaProcessResponse startScaProcessResponse);

    StartScaProcessResponse toStartScaProcessResponse(StartScaProcessResponseTO startScaProcessResponse);

    UpdateAuthorisationResponseTO toUpdateAuthorisationResponseTO(UpdateAuthorisationResponse updateAuthorisationResponse);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(UpdateAuthorisationResponseTO updateAuthorisationResponse);

    CardAccountListTO toCardAccountListTO(CardAccountList cardAccountList);

    CardAccountList toCardAccountList(CardAccountListTO cardAccountList);

    CardAccountDetailsResponseTO toCardAccountDetailsResponseTO(CardAccountDetailsResponse cardAccountDetailsResponse);

    CardAccountDetailsResponse toCardAccountDetailsResponse(CardAccountDetailsResponseTO cardAccountDetailsResponse);

    ReadCardAccountBalanceResponseTO toReadCardAccountBalanceResponseTO(ReadCardAccountBalanceResponse source);

    ReadCardAccountBalanceResponse toReadCardAccountBalanceResponse(ReadCardAccountBalanceResponseTO source);

    CardAccountsTransactionsResponseTO toCardAccountsTransactionsResponseTO(CardAccountsTransactionsResponse source);

    CardAccountsTransactionsResponse toCardAccountsTransactionsResponse(CardAccountsTransactionsResponseTO source);

    PaymentInitiation toPaymentInitiation(PaymentInitiationTO source);

    PaymentInitiationRequestResponseTO toPaymentInitiationRequestResponseTO(PaymentInitiationRequestResponse source);

    @Named("toGetPaymentInformationResponseTO")
    default Object toGetPaymentInformationResponseTO(Object source) {
        if (source == null || source instanceof String) {
            return source;
        }
        if (source instanceof PaymentInitiationWithStatusResponse) {
            return toPaymentInitiationWithStatusResponseTO((PaymentInitiationWithStatusResponse) source);
        }
        if (source instanceof PeriodicPaymentInitiationWithStatusResponse) {
            return toPeriodicPaymentInitiationWithStatusResponseTO((PeriodicPaymentInitiationWithStatusResponse) source);
        }
        if (source instanceof BulkPaymentInitiationWithStatusResponse) {
            return toBulkPaymentInitiationWithStatusResponse((BulkPaymentInitiationWithStatusResponse) source);
        }
        throw new IllegalArgumentException(source.getClass().getName());
    }

    PaymentInitiationWithStatusResponseTO toPaymentInitiationWithStatusResponseTO(
        PaymentInitiationWithStatusResponse source);

    PeriodicPaymentInitiationWithStatusResponseTO toPeriodicPaymentInitiationWithStatusResponseTO(
        PeriodicPaymentInitiationWithStatusResponse source);

    BulkPaymentInitiationWithStatusResponseTO toBulkPaymentInitiationWithStatusResponse(
        BulkPaymentInitiationWithStatusResponse source);

    @Named("toGetPaymentInitiationStatusResponseTO")
    default Object toGetPaymentInitiationStatusResponseTO(Object source) {
        if (source == null || source instanceof String) {
            return source;
        }
        if (source instanceof PaymentInitiationStatusResponse) {
            return toPaymentInitiationStatusResponseTO((PaymentInitiationStatusResponse) source);
        }
        throw new IllegalArgumentException(source.getClass().getName());
    }

    PaymentInitiationStatusResponseTO toPaymentInitiationStatusResponseTO(PaymentInitiationStatusResponse source);

    AuthorisationsTO toAuthorisationsTO(Authorisations source);
}
