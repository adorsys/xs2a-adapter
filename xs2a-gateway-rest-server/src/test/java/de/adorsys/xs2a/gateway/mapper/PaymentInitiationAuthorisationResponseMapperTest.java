package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.Authorisations;
import de.adorsys.xs2a.gateway.model.shared.AuthorisationsList;
import de.adorsys.xs2a.gateway.service.PaymentInitiationAuthorisationResponse;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentInitiationAuthorisationResponseMapperTest {
    private static final String FIRST_AUTHORISATION_ID = "firstAuthorisationId";
    private static final String SECOND_AUTHORISATION_ID = "secondAuthorisationId";
    private static final String THIRD_AUTHORISATION_ID = "thirdAuthorisationId";

    @Test
    public void toAuthorisations() {
        PaymentInitiationAuthorisationResponseMapper mapper = Mappers.getMapper(PaymentInitiationAuthorisationResponseMapper.class);
        Authorisations authorisations = mapper.toAuthorisations(buildPaymentInitiationAuthorisationResponse());

        assertThat(authorisations).isNotNull();

        AuthorisationsList authorisationsList = authorisations.getAuthorisationIds();
        assertThat(authorisationsList).isNotNull();
        assertThat(authorisationsList).containsExactlyInAnyOrder(FIRST_AUTHORISATION_ID, SECOND_AUTHORISATION_ID, THIRD_AUTHORISATION_ID);
    }

    static PaymentInitiationAuthorisationResponse buildPaymentInitiationAuthorisationResponse() {
        return new PaymentInitiationAuthorisationResponse(Arrays.asList(FIRST_AUTHORISATION_ID, SECOND_AUTHORISATION_ID, THIRD_AUTHORISATION_ID));
    }
}