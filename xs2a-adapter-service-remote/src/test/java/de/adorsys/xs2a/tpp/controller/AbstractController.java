package de.adorsys.xs2a.tpp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.mapper.StartScaProcessResponseMapper;
import de.adorsys.xs2a.adapter.service.model.ErrorResponse;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.TppMessage;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

public abstract class AbstractController {
    final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ObjectMapper objectMapper;

    protected AbstractController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    Response<?> handleAuthorisationBody(ObjectNode body, AuthorisationBodyHandler... handlers) {
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

        Response<?> apply(ObjectNode body, ObjectMapper objectMapper);

        Response<?> apply(T t);
    }

    @FunctionalInterface
    interface UpdatePsuAuthenticationHandler extends AuthorisationBodyHandler<UpdatePsuAuthentication> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("psuData");
        }

        default Response<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, UpdatePsuAuthentication.class));
        }
    }

    @FunctionalInterface
    interface SelectPsuAuthenticationMethodHandler extends AuthorisationBodyHandler<SelectPsuAuthenticationMethod> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("authenticationMethodId");
        }

        default Response<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, SelectPsuAuthenticationMethod.class));
        }
    }

    @FunctionalInterface
    interface TransactionAuthorisationHandler extends AuthorisationBodyHandler<TransactionAuthorisation> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("scaAuthenticationData");
        }

        default Response<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, TransactionAuthorisation.class));
        }
    }

    @FunctionalInterface
    interface StartAuthorisationHandler extends AuthorisationBodyHandler<StartAuthorisationHandler.EmptyAuthorisationBody> {

        class EmptyAuthorisationBody {
        }

        default boolean isApplicable(ObjectNode body) {
            return !body.fields().hasNext();
        }

        default Response<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(new EmptyAuthorisationBody());
        }
    }
}
