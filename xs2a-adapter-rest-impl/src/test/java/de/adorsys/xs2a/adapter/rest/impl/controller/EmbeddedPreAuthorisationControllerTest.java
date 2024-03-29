/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.rest.impl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.mapper.Oauth2Mapper;
import de.adorsys.xs2a.adapter.rest.api.model.EmbeddedPreAuthorisationRequestTO;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmbeddedPreAuthorisationControllerTest {

    private MockMvc mockMvc;

    private final Oauth2Mapper mapper = Mappers.getMapper(Oauth2Mapper.class);

    @Mock
    private EmbeddedPreAuthorisationService authorisationService;

    @InjectMocks
    private EmbeddedPreAuthorisationController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                      .setMessageConverters(new MappingJackson2HttpMessageConverter())
                      .build();
    }

    @Test
    void getToken() throws Exception {

        EmbeddedPreAuthorisationRequestTO request = new EmbeddedPreAuthorisationRequestTO();
        TokenResponse token = new TokenResponse();
        token.setAccessToken("access-token");
        token.setScope("scope");
        token.setRefreshToken("refresh-token");
        token.setExpiresInSeconds(3600L);
        token.setTokenType("token-type");

        when(authorisationService.getToken(any(), any()))
            .thenReturn(token);

        MvcResult mvcResult = mockMvc.perform(post("/v1/embedded-pre-auth/token")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(writeValueAsString(request)))
                                  .andExpect(status().is(HttpStatus.OK.value()))
                                  .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        TokenResponseTO actual = jsonStringToObject(response.getContentAsString());

        assertThat(actual).isEqualTo(mapper.map(token));
    }

    private <T> String writeValueAsString(T value) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(value);
    }

    private TokenResponseTO jsonStringToObject(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, TokenResponseTO.class);
    }
}
