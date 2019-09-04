package de.adorsys.xs2a.adapter.service.loader.mapper;

import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentStatusResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.Consents;
import de.adorsys.xs2a.adapter.service.psd2.model.ScaStatusResponse;
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

    StartScaprocessResponse toStartScaprocessResponse(StartScaProcessResponse value);

    SelectPsuAuthenticationMethod toSelectPsuAuthenticationMethod(UpdateAuthorisation updateAuthentication);

    UpdatePsuAuthentication toUpdatePsuAuthentication(UpdateAuthorisation updateAuthentication);

    TransactionAuthorisation toTransactionAuthorisation(UpdateAuthorisation updateAuthorisation);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse value);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse value);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(de.adorsys.xs2a.adapter.service.model.ScaStatusResponse value);
}
