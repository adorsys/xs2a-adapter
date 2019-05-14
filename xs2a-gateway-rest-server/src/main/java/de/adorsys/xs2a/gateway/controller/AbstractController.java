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

public abstract class AbstractController {
    final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ObjectMapper objectMapper;

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

    GeneralResponse handleAuthorisationBody(ObjectNode body, AuthorisationBodyHandler... handlers) {
        for (AuthorisationBodyHandler handler : handlers) {
            if (handler.isApplicable(body)) {
                return handler.apply(body, objectMapper);
            }
        }

        ErrorResponse errorResponse = new ErrorResponse();
        TppMessage tppMessage = new TppMessage();
        tppMessage.setText("Request body doesn't match any of the supported schemas");
        errorResponse.setTppMessages(Collections.singletonList(tppMessage));
        throw new ErrorResponseException(400, ResponseHeaders.emptyResponseHeaders(), errorResponse);
    }

    interface AuthorisationBodyHandler<T> {
        boolean isApplicable(ObjectNode body);

        GeneralResponse apply(ObjectNode body, ObjectMapper objectMapper);

        GeneralResponse apply(T t);
    }

    @FunctionalInterface
    interface UpdatePsuAuthenticationHandler extends AuthorisationBodyHandler<UpdatePsuAuthentication> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("psuData");
        }

        default GeneralResponse<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, UpdatePsuAuthentication.class));
        }
    }

    @FunctionalInterface
    interface SelectPsuAuthenticationMethodHandler extends AuthorisationBodyHandler<SelectPsuAuthenticationMethod> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("authenticationMethodId");
        }

        default GeneralResponse<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, SelectPsuAuthenticationMethod.class));
        }
    }

    @FunctionalInterface
    interface TransactionAuthorisationHandler extends AuthorisationBodyHandler<TransactionAuthorisation> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("scaAuthenticationData");
        }

        default GeneralResponse<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, TransactionAuthorisation.class));
        }
    }
}
