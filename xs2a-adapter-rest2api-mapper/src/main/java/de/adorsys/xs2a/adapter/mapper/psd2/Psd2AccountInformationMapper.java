package de.adorsys.xs2a.adapter.mapper.psd2;

import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.Mapper;

@Mapper
public interface Psd2AccountInformationMapper {
    ConsentsResponseTO toConsentsResponseTO(ConsentsResponse consentsResponse);

    Consents toConsents(ConsentsTO consents);

    ConsentInformationResponseTO toConsentInformationResponseTO(ConsentInformationResponse consentInformationResponse);

    ConsentStatusResponseTO toConsentStatusResponseTO(ConsentStatusResponse consentStatusResponse);

    ScaStatusResponseTO toScaStatusResponseTO(ScaStatusResponse consentScaStatus);

    AccountListTO toAccountListTO(AccountList accountList);

    ReadAccountBalanceResponseTO toReadAccountBalanceResponseTO(ReadAccountBalanceResponse balances);

    TransactionsResponseTO toTransactionsResponseTO(TransactionsResponse transactions);

    UpdateAuthorisation map(UpdateAuthorisationTO value);

    StartScaprocessResponseTO map(StartScaprocessResponse value);

    UpdateAuthorisationResponseTO map(UpdateAuthorisationResponse updateConsentsPsuData);
}
