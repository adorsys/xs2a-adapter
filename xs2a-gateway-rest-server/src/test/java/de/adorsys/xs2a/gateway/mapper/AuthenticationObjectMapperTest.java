package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.pis.AuthenticationObjectTO;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static de.adorsys.xs2a.gateway.TestModelBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationObjectMapperTest {

    @Test
    public void toAuthenticationObjectTO() {
        AuthenticationObjectMapper mapper = Mappers.getMapper(AuthenticationObjectMapper.class);
        AuthenticationObjectTO object = mapper.toAuthenticationObjectTO(buildAuthenticationObject());
        assertThat(object.getAuthenticationMethodId()).isEqualTo(METHOD_ID);
        assertThat(object.getAuthenticationVersion()).isEqualTo(VERSION);
        assertThat(object.getAuthenticationType().name()).isEqualTo(TYPE);
        assertThat(object.getExplanation()).isEqualTo(EXPLANATION);
        assertThat(object.getName()).isEqualTo(NAME);
    }


}