package de.adorsys.xs2a.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.gateway.mapper.StartScaProcessResponseMapper;
import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;
import de.adorsys.xs2a.gateway.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.gateway.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.UUID;
import java.util.function.Function;

public abstract class AbstractController {
    final ObjectMapper objectMapper;
    final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);

    protected AbstractController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    RequestHeaders buildHeaders(String bankCode, UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        return RequestHeaders.builder()
                .bankCode(bankCode)
                .xRequestId(xRequestID)
                .digest(digest)
                .signature(signature)
                .tppSignatureCertificate(tpPSignatureCertificate)
                .psuIpAddress(psUIPAddress)
                .psuIpPort(psUIPPort)
                .psuAccept(psUAccept)
                .psuAcceptCharset(psUAcceptCharset)
                .psuAcceptEncoding(psUAcceptEncoding)
                .psuAcceptLanguage(psUAcceptLanguage)
                .psuUserAgent(psUUserAgent)
                .psuHttpMethod(psUHttpMethod)
                .psuDeviceId(psUDeviceID)
                .psuGeoLocation(psUGeoLocation)
                .build();
    }

    GeneralResponse<?> handleAuthorisationBody(ObjectNode body,
                                               Function<UpdatePsuAuthentication, GeneralResponse<?>> updatePsuAuthenticationHandler,
                                               Function<SelectPsuAuthenticationMethod, GeneralResponse<?>> selectPsuAuthenticationMethodHandler,
                                               Function<TransactionAuthorisation, GeneralResponse<?>> transactionAuthorisationHandler) {
        if (body.has("psuData") && updatePsuAuthenticationHandler != null) {
            UpdatePsuAuthentication updatePsuAuthentication
                    = objectMapper.convertValue(body, UpdatePsuAuthentication.class);

            return updatePsuAuthenticationHandler.apply(updatePsuAuthentication);
        }
        if (body.has("authenticationMethodId") && selectPsuAuthenticationMethodHandler != null) {
            SelectPsuAuthenticationMethod selectPsuAuthenticationMethod
                    = objectMapper.convertValue(body, SelectPsuAuthenticationMethod.class);

            return selectPsuAuthenticationMethodHandler.apply(selectPsuAuthenticationMethod);
        }
        if (body.has("scaAuthenticationData") && transactionAuthorisationHandler != null) {
            TransactionAuthorisation transactionAuthorisation
                    = objectMapper.convertValue(body, TransactionAuthorisation.class);

            return transactionAuthorisationHandler.apply(transactionAuthorisation);
        }

        ErrorResponse errorResponse = new ErrorResponse();
        TppMessage tppMessage = new TppMessage();
        tppMessage.setText("Request body doesn't match any of the supported schemas");
        errorResponse.setTppMessages(Collections.singletonList(tppMessage));
        throw new ErrorResponseException(400, ResponseHeaders.emptyResponseHeaders(), errorResponse);
    }
}
