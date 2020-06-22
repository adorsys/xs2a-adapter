package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        Response<ConsentsResponse201> response = new Response<>(HTTP_CODE_200,
            new ConsentsResponse201(),
            ResponseHeaders.fromMap(Collections.emptyMap()));

        when(adapterServiceLoader.getAccountInformationService(any()))
            .thenReturn(accountInformationService);

        when(accountInformationService.createConsent(any(), any(), any())).thenReturn(response);

        Response<ConsentsResponse201> consentResponse =
            service.createConsent(RequestHeaders.fromMap(Collections.singletonMap(RequestHeaders.X_GTW_ASPSP_ID, "BIC")),
                RequestParams.empty(),
                new Consents());

        verify(accountInformationService, times(1)).createConsent(any(), any(), any());

        assertThat(consentResponse).isEqualTo(response);
    }
}
