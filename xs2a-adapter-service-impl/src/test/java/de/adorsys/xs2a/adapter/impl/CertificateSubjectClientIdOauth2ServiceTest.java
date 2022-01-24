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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CertificateSubjectClientIdOauth2ServiceTest {

    private static final String organisationIdentifier = "organisationIdentifier";

    private Pkcs12KeyStore pkcs12KeyStore = mock(Pkcs12KeyStore.class);
    private Oauth2Service oauth2Service = mock(Oauth2Service.class);
    private CertificateSubjectClientIdOauth2Service service = new CertificateSubjectClientIdOauth2Service(oauth2Service, pkcs12KeyStore);
    private ArgumentCaptor<Parameters> captor = ArgumentCaptor.forClass(Parameters.class);

    private Parameters parameters = new Parameters();

    @Test
    void getAuthorizationRequestUri() throws IOException, KeyStoreException {
        String authorisationRequestUri = "https://authorisation.endpoint?" +
            "response_type=code&state=state&redirect_uri=https%3A%2F%2Fredirect";

        URI expectedOutput = URI.create(authorisationRequestUri + "&" + Parameters.CLIENT_ID + "=" + organisationIdentifier);

        when(oauth2Service.getAuthorizationRequestUri(any(), any())).thenReturn(URI.create(authorisationRequestUri));
        when(pkcs12KeyStore.getOrganizationIdentifier()).thenReturn(organisationIdentifier);

        URI actual = service.getAuthorizationRequestUri(null, parameters);

        verify(oauth2Service, times(1)).getAuthorizationRequestUri(any(), any());
        verify(pkcs12KeyStore, times(1)).getOrganizationIdentifier();

        assertEquals(actual, expectedOutput);
    }

    @Test
    void getAuthorizationRequestUri_throwingException() throws IOException, KeyStoreException {
        when(oauth2Service.getAuthorizationRequestUri(any(), any())).thenReturn(URI.create(""));
        when(pkcs12KeyStore.getOrganizationIdentifier()).thenThrow(KeyStoreException.class);

        assertThrows(RuntimeException.class, () -> service.getAuthorizationRequestUri(null, parameters));
    }

    @Test
    void getToken() throws KeyStoreException, IOException {
        when(pkcs12KeyStore.getOrganizationIdentifier()).thenReturn(organisationIdentifier);
        when(oauth2Service.getToken(any(), eq(parameters))).thenReturn(new TokenResponse());

        service.getToken(null, parameters);

        verify(pkcs12KeyStore, times(1)).getOrganizationIdentifier();
        verify(oauth2Service, times(1)).getToken(any(), captor.capture());

        assertEquals(organisationIdentifier, captor.getValue().getClientId());
    }
}
