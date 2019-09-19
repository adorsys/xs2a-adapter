package de.adorsys.xs2a.adapter.mapper.psd2;

import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.Mapper;

@Mapper
public interface Psd2AccountInformationMapper {
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
}
