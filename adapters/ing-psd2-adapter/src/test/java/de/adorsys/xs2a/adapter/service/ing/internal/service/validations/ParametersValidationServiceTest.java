package de.adorsys.xs2a.adapter.service.ing.internal.service.validations;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.ing.internal.service.ParametersValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersValidationServiceTest {

    private static final String DEFAULT_SCOPE = "payment-accounts:transactions:view";
    private static final String BALANCES_SCOPE = "payment-accounts:balances:view";

    private Oauth2Service.Parameters parameters;

    @BeforeEach
    void setUp() {
        parameters = new Oauth2Service.Parameters();
    }

    @Test
    void validate_noScope() {
        Oauth2Service.Parameters actual = ParametersValidationService.validateScope(parameters);

        assertNotNull(actual.getScope());
        assertEquals(actual.getScope(), DEFAULT_SCOPE);
    }

    @Test
    void validate_wrongScopes() {
        parameters.setScope("some scope");

        Oauth2Service.Parameters actual = ParametersValidationService.validateScope(parameters);

        assertNotNull(actual.getScope());
        assertEquals(actual.getScope(), DEFAULT_SCOPE);
    }

    @Test
    void validate_rightScope() {
        parameters.setScope(DEFAULT_SCOPE);

        Oauth2Service.Parameters actual = ParametersValidationService.validateScope(parameters);

        assertEquals(actual, parameters);
    }

    @Test
    void validate_multipleWithWrongScope() {
        parameters.setScope(DEFAULT_SCOPE + " " + BALANCES_SCOPE + " some scope");

        Oauth2Service.Parameters actual = ParametersValidationService.validateScope(parameters);

        assertNotNull(actual.getScope());
        assertEquals(actual.getScope(), DEFAULT_SCOPE + " " + BALANCES_SCOPE);
    }

    @Test
    void validate_multipleRightScopes() {
        parameters.setScope(DEFAULT_SCOPE + " " + BALANCES_SCOPE);

        Oauth2Service.Parameters actual = ParametersValidationService.validateScope(parameters);

        assertNotNull(actual.getScope());
        assertEquals(actual.getScope(), DEFAULT_SCOPE + " " + BALANCES_SCOPE);
    }
}
