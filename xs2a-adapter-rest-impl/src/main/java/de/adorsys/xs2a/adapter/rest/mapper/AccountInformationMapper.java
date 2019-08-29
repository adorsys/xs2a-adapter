package de.adorsys.xs2a.adapter.rest.mapper;

import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountInformationMapper {
    ConsentsResponseTO toConsentsResponseTO(ConsentsResponse consentsResponse);

    Consents toConsents(ConsentsTO consents);

    ConsentInformationResponseTO toConsentInformationResponseTO(ConsentInformationResponse consentInformationResponse);

    ConsentStatusResponseTO toConsentStatusResponseTO(ConsentStatusResponse consentStatusResponse);

    ScaStatusResponseTO toScaStatusResponseTO(ScaStatusResponse consentScaStatus);

    AccountListTO toAccountListTO(AccountList accountList);

    ReadAccountBalanceResponseTO toReadAccountBalanceResponseTO(ReadAccountBalanceResponse balances);

    TransactionsResponseTO toTransactionsResponseTO(TransactionsResponse transactions);
}
