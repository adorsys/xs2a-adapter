package de.adorsys.xs2a.adapter.fiducia.mapper;

import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PaymentInitiationStatusResponse200Json;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationJson;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationMultipartBody;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.api.model.SelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.api.model.StartScaprocessResponse;
import de.adorsys.xs2a.adapter.api.model.TransactionDetailsBody;
import de.adorsys.xs2a.adapter.api.model.Transactions;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.api.model.TransactionStatus;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaOK200TransactionDetails;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationJson;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationMultipartBody;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaStartScaProcessResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaTransactionDetails;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaUpdatePsuAuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
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
    Transactions toTransactionDetails(FiduciaTransactionDetails value);

    @Mapping(target = "transactionDetails", expression = "java(toTransactionDetails(value))")
    TransactionDetailsBody toTransactionDetailsBody(FiduciaTransactionDetails value);

    PaymentInitationRequestResponse201 toPaymentInitationRequestResponse201(FiduciaPaymentInitationRequestResponse201 value);

    default PaymentInitiationStatusResponse200Json toPaymentInitiationStatusResponse200Json(String xmlString) {
        PaymentInitiationStatusResponse200Json response = new PaymentInitiationStatusResponse200Json();
        if (xmlString == null || xmlString.isBlank()) {
            response.setTransactionStatus(TransactionStatus.RCVD); // Default to 'Received' for empty/null XML
            return response;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlString));
            Document doc = builder.parse(is);
            String statusString = doc.getElementsByTagName("GrpSts").item(0).getTextContent();
            TransactionStatus transactionStatus = TransactionStatus.fromValue(statusString);
            response.setTransactionStatus(transactionStatus);
        } catch (Exception e) {
            // Handle parsing error, e.g., log it or set a default status
            response.setTransactionStatus(TransactionStatus.RCVD); // Default to 'Received' on error
        }
        return response;
    }
}
