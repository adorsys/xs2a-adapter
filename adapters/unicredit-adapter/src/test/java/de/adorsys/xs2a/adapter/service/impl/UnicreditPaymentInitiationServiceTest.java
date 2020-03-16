package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UnicreditPaymentInitiationServiceTest {

    private UnicreditPaymentInitiationService unicreditPaymentInitiationService;

    @BeforeEach
    void setUp() {
        unicreditPaymentInitiationService = new UnicreditPaymentInitiationService(null, null, null);
    }

    @Test
    void validateGetPaymentInitiationScaStatus() {
        List<ValidationError> validationErrors =
            unicreditPaymentInitiationService.validateGetPaymentInitiationScaStatus("non-valid-payment-service",
                null,
                null,
                null,
                RequestHeaders.fromMap(Collections.singletonMap(RequestHeaders.ACCEPT, "*/*")),
                null);
        assertThat(validationErrors).extracting(ValidationError::getPath)
            .containsExactly("paymentService", RequestHeaders.ACCEPT);

    }
}
