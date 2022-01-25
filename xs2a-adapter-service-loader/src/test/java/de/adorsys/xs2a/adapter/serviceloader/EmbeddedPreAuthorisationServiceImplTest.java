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

import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EmbeddedPreAuthorisationServiceImplTest {

    @Test
    void getToken() {
        AdapterServiceLoader serviceLoader = mock(AdapterServiceLoader.class);
        EmbeddedPreAuthorisationService service = mock(EmbeddedPreAuthorisationService.class);
        EmbeddedPreAuthorisationServiceImpl serviceImpl = new EmbeddedPreAuthorisationServiceImpl(serviceLoader);
        RequestHeaders requestHeaders = RequestHeaders.fromMap(Collections.emptyMap());
        EmbeddedPreAuthorisationRequest authorisationRequest = new EmbeddedPreAuthorisationRequest();
        TokenResponse expected = new TokenResponse();

        doReturn(service).when(serviceLoader).getEmbeddedPreAuthorisationService(eq(requestHeaders));
        doReturn(expected).when(service).getToken(authorisationRequest, requestHeaders);

        TokenResponse actual = serviceImpl.getToken(authorisationRequest, requestHeaders);

        assertThat(actual).isEqualTo(expected);

        verify(serviceLoader, times(1)).getEmbeddedPreAuthorisationService(any());
        verify(service, times(1)).getToken(any(), any());
    }
}
