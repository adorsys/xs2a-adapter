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

package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdapterDelegatingOauth2ServiceTest {

    @Spy
    private final Oauth2Service testService = new TestOauth2Service();

    @Mock
    private AdapterServiceLoader serviceLoader;

    @InjectMocks
    private AdapterDelegatingOauth2Service oauth2Service;

    @Test
    void getAuthorizationRequestUri() throws IOException {
        when(serviceLoader.getOauth2Service(any(RequestHeaders.class))).thenReturn(testService);

        oauth2Service.getAuthorizationRequestUri(new HashMap<>(), new Oauth2Service.Parameters());

        verify(serviceLoader, times(1)).getOauth2Service(any(RequestHeaders.class));
        verify(testService, times(1)).getAuthorizationRequestUri(anyMap(), any(Oauth2Service.Parameters.class));
    }

    @Test
    void getToken() throws IOException {
        when(serviceLoader.getOauth2Service(any(RequestHeaders.class))).thenReturn(testService);

        oauth2Service.getToken(new HashMap<>(), new Oauth2Service.Parameters());

        verify(serviceLoader, times(1)).getOauth2Service(any(RequestHeaders.class));
        verify(testService, times(1)).getToken(anyMap(), any(Oauth2Service.Parameters.class));
    }
}
