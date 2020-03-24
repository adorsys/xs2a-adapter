package de.adorsys.xs2a.adapter.service.loader.mapper;

import de.adorsys.xs2a.adapter.service.model.CardAccountList;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentStatusResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.Consents;
import de.adorsys.xs2a.adapter.service.psd2.model.PaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.ScaStatusResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.TransactionDetails;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Xs2aPsd2Mapper {
    AccountList toAccountList(AccountListHolder value);

    ReadAccountBalanceResponse toReadAccountBalanceResponse(BalanceReport value);

    TransactionsResponse toTransactionsResponse(TransactionsReport value);

    @Mapping(target = "purposeCode", source = "purposeCode.code")
    @Mapping(target = "bankTransactionCode", source = "bankTransactionCode.code")
    TransactionDetails map(Transactions value);

    default String map(Enum<?> value) {
        return value == null ? null : value.toString();
    }

    de.adorsys.xs2a.adapter.service.model.Consents map(Consents value);

    ConsentsResponse toConsentsResponse(ConsentCreationResponse value);

    ConsentInformationResponse toConsentInformationResponse(ConsentInformation value);

    ConsentStatusResponse toConsentStatusResponse(de.adorsys.xs2a.adapter.service.model.ConsentStatusResponse value);

    ScaStatusResponse toScaStatusResponse(de.adorsys.xs2a.adapter.service.model.ScaStatusResponse value);

    UpdatePsuAuthentication map(UpdateAuthorisation value);

    StartScaProcessResponse toStartScaprocessResponse(de.adorsys.xs2a.adapter.service.model.StartScaProcessResponse value);

    SelectPsuAuthenticationMethod toSelectPsuAuthenticationMethod(UpdateAuthorisation updateAuthentication);

    UpdatePsuAuthentication toUpdatePsuAuthentication(UpdateAuthorisation updateAuthentication);

    TransactionAuthorisation toTransactionAuthorisation(UpdateAuthorisation updateAuthorisation);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse value);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse value);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(de.adorsys.xs2a.adapter.service.model.ScaStatusResponse value);

    de.adorsys.xs2a.adapter.service.psd2.model.CardAccountList toCardAccountList(CardAccountList value);

    CardAccountDetailsResponse toCardAccountDetailsResponse(CardAccountDetailsHolder value);

    ReadCardAccountBalanceResponse toReadCardAccountBalanceResponse(CardAccountBalanceReport value);

    CardAccountsTransactionsResponse toCardAccountsTransactionsResponse(CardAccountsTransactions value);

    PaymentInitiationRequestResponse toPaymentInitiationRequestResponse(
        de.adorsys.xs2a.adapter.service.model.PaymentInitiationRequestResponse value);

    SinglePaymentInitiationBody toSinglePaymentInitiationBody(PaymentInitiation value);

    PaymentInitiationWithStatusResponse toPaymentInitiationWithStatusResponse(
        SinglePaymentInitiationInformationWithStatusResponse value);

    Authorisations toAuthorisations(PaymentInitiationAuthorisationResponse value);

    ScaStatusResponse toScaStatusResponse(PaymentInitiationScaStatusResponse value);
}
