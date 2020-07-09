package de.adorsys.xs2a.adapter.fiducia.mapper;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.fiducia.model.*;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper
public interface FiduciaMapper {
    SelectPsuAuthenticationMethodResponse toSelectPsuAuthenticationMethodResponse(FiduciaUpdatePsuDataResponse value);

    default List<String> toListOfStrings(String challengeData) {
        if (challengeData == null) {
            return null;
        }
        return Collections.singletonList(challengeData);
    }

    StartScaprocessResponse toStartScaProcessResponse(FiduciaStartScaProcessResponse value);

    FiduciaPeriodicPaymentInitiationJson toFiduciaPeriodicPaymentInitiationJson(PeriodicPaymentInitiationJson value);

    PeriodicPaymentInitiationWithStatusResponse toPeriodicPaymentInitiationWithStatusResponse(
        FiduciaPeriodicPaymentInitiationWithStatusResponse value);

    PeriodicPaymentInitiationMultipartBody toPeriodicPaymentInitiationMultipartBody(
        FiduciaPeriodicPaymentInitiationMultipartBody value);

    TransactionsResponse200Json toTransactionsResponse200Json(FiduciaTransactionsResponse200Json value);

    OK200TransactionDetails toOK200TransactionDetails(FiduciaOK200TransactionDetails value);
}
