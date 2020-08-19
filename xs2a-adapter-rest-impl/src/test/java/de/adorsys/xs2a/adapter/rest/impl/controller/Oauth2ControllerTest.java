package de.adorsys.xs2a.adapter.rest.impl.controller;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.rest.api.Oauth2Api;
import de.adorsys.xs2a.adapter.rest.impl.TestModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Oauth2ControllerTest {

    public static final String AUTHORISATION_URL = "https://authorisation.url";

    @InjectMocks
    private Oauth2Controller oauth2Controller;

    @Mock
    private Oauth2Service oauth2Service;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(oauth2Controller).build();
    }

    @Test
    void getAuthorizationUrl() throws Exception {
        when(oauth2Service.getAuthorizationRequestUri(anyMap(), any()))
            .thenReturn(URI.create(AUTHORISATION_URL));

        mockMvc.perform(get(Oauth2Api.AUTHORIZATION_REQUEST_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.href", containsString(AUTHORISATION_URL)));

        verify(oauth2Service, times(1))
            .getAuthorizationRequestUri(anyMap(), any());
    }

    @Test
    void getToken() throws Exception {
        when(oauth2Service.getToken(anyMap(), any()))
            .thenReturn(TestModelBuilder.buildTokenResponse());

        mockMvc.perform(post("/oauth2/token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token", containsString(TestModelBuilder.ACCESS_TOKEN)))
            .andExpect(jsonPath("$.refresh_token", containsString(TestModelBuilder.REFRESH_TOKEN)))
            .andExpect(jsonPath("$.scope", containsString(TestModelBuilder.SCOPE)))
            .andExpect(jsonPath("$.expires_in", equalTo((int) TestModelBuilder.exripesInSeconds)))
            .andExpect(jsonPath("$.token_type", containsString(TestModelBuilder.TOKEN_TYPE)));

        verify(oauth2Service, times(1))
            .getToken(anyMap(), any());
    }
}
