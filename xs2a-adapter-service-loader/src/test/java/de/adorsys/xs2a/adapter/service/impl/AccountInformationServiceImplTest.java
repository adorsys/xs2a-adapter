package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountInformationServiceImplTest {
    private static final int HTTP_CODE_200 = 200;

    @InjectMocks
    private AccountInformationServiceImpl service;

    @Mock
    private AccountInformationService accountInformationService;
    @Mock
    private AdapterServiceLoader adapterServiceLoader;

    @Test
    public void createConsent() {
        Response<ConsentCreationResponse> response = new Response<>(HTTP_CODE_200, new ConsentCreationResponse(), ResponseHeaders.fromMap(Collections.emptyMap()));

        when(adapterServiceLoader.getAccountInformationService(any()))
            .thenReturn(accountInformationService);

        when(accountInformationService.createConsent(any(), any())).thenReturn(response);

        Response<ConsentCreationResponse> consentResponse = service.createConsent(RequestHeaders.fromMap(Collections.singletonMap(RequestHeaders.X_GTW_ASPSP_ID, "BIC")), new Consents());

        verify(accountInformationService, times(1)).createConsent(any(), any());

        assertThat(consentResponse).isEqualTo(response);
    }
}
