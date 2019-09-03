package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.mapper.StartScaProcessResponseMapper;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import org.mapstruct.factory.Mappers;

public abstract class AbstractController {
    final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ObjectMapper objectMapper;

    protected AbstractController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    GeneralResponse<?> handleAuthorisationBody(ObjectNode body, AuthorisationBodyHandler... handlers) {
        for (AuthorisationBodyHandler handler : handlers) {
            if (handler.isApplicable(body)) {
                return handler.apply(body, objectMapper);
            }
        }

        throw new BadRequestException("Request body doesn't match any of the supported schemas");
    }

    interface AuthorisationBodyHandler<T> {
        boolean isApplicable(ObjectNode body);

        GeneralResponse<?> apply(ObjectNode body, ObjectMapper objectMapper);

        GeneralResponse<?> apply(T t);
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

    @FunctionalInterface
    interface StartAuthorisationHandler extends AuthorisationBodyHandler<StartAuthorisationHandler.EmptyAuthorisationBody> {

        class EmptyAuthorisationBody {
        }

        default boolean isApplicable(ObjectNode body) {
            return !body.fields().hasNext();
        }

        default GeneralResponse<?> apply(ObjectNode body, ObjectMapper objectMapper) {
            return apply(new EmptyAuthorisationBody());
        }
    }
}
