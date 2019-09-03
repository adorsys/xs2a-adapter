package de.adorsys.xs2a.adapter.service.loader.mapper;

import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentStatusResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.Consents;
import de.adorsys.xs2a.adapter.service.psd2.model.ScaStatusResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface Xs2aPsd2Mapper {
    AccountList map(AccountListHolder value);

    ReadAccountBalanceResponse map(BalanceReport value);

    TransactionsResponse map(TransactionsReport value);

    @Mapping(target = "purposeCode", source = "purposeCode.code")
    @Mapping(target = "bankTransactionCode", source = "bankTransactionCode.code")
    TransactionDetails map(Transactions value);

    default String map(Enum<?> value) {
        return value == null ? null : value.toString();
    }

    de.adorsys.xs2a.adapter.service.model.Consents map(Consents value);

    ConsentsResponse map(ConsentCreationResponse value);

    ConsentInformationResponse map(ConsentInformation value);

    ConsentStatusResponse map(de.adorsys.xs2a.adapter.service.model.ConsentStatusResponse value);

    ScaStatusResponse map(de.adorsys.xs2a.adapter.service.model.ScaStatusResponse value);

    UpdatePsuAuthentication map(UpdateAuthorisation value);

    StartScaprocessResponse map(StartScaProcessResponse value);

    void map(UpdateAuthorisation updateAuthentication, @MappingTarget SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    void map(UpdateAuthorisation updateAuthentication, @MappingTarget UpdatePsuAuthentication updatePsuAuthentication);

    void map(UpdateAuthorisation updateAuthorisation, @MappingTarget TransactionAuthorisation transactionAuthorisation);

    UpdateAuthorisationResponse map(de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse value);

    UpdateAuthorisationResponse map(de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse value);

    UpdateAuthorisationResponse toUpdateAuthorisationResponse(de.adorsys.xs2a.adapter.service.model.ScaStatusResponse value);
}
