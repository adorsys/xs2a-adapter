package de.adorsys.xs2a.adapter.rest.impl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.exception.BadRequestException;
import de.adorsys.xs2a.adapter.api.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.api.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthentication;

public abstract class AbstractController {
    private final ObjectMapper objectMapper;

    protected AbstractController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    Response handleAuthorisationBody(ObjectNode body, AuthorisationBodyHandler... handlers) {
        for (AuthorisationBodyHandler handler : handlers) {
            if (handler.isApplicable(body)) {
                return handler.apply(body, objectMapper);
            }
        }

        throw new BadRequestException("Request body doesn't match any of the supported schemas");
    }

    interface AuthorisationBodyHandler<T> {
        boolean isApplicable(ObjectNode body);

        Response apply(ObjectNode body, ObjectMapper objectMapper);

        Response apply(T t);
    }

    @FunctionalInterface
    interface UpdatePsuAuthenticationHandler extends AuthorisationBodyHandler<UpdatePsuAuthentication> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("psuData");
        }

        default Response apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, UpdatePsuAuthentication.class));
        }
    }

    @FunctionalInterface
    interface SelectPsuAuthenticationMethodHandler extends AuthorisationBodyHandler<SelectPsuAuthenticationMethod> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("authenticationMethodId");
        }

        default Response apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(objectMapper.convertValue(body, SelectPsuAuthenticationMethod.class));
        }
    }

    @FunctionalInterface
    interface TransactionAuthorisationHandler extends AuthorisationBodyHandler<TransactionAuthorisation> {

        default boolean isApplicable(ObjectNode body) {
            return body.has("scaAuthenticationData");
        }

        default Response apply(ObjectNode body, ObjectMapper objectMapper) {
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

        default Response apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(new EmptyAuthorisationBody());
        }
    }
}
