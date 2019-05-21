package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.ResponseHeaders;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.ais.Consents;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ConsentServiceImplTest {
    private static final int HTTP_CODE_200 = 200;

    @Test
    public void createConsent() {
        GeneralResponse<ConsentCreationResponse> response = new GeneralResponse<>(HTTP_CODE_200, new ConsentCreationResponse(), ResponseHeaders.fromMap(Collections.emptyMap()));
        AccountInformationService accountInformationService = mock(AccountInformationService.class);
        AccountInformationServiceImpl service = new AccountInformationServiceImpl(){
            @Override
            AccountInformationService getAccountInformationService(RequestHeaders headers) {
                return accountInformationService;
            }
        };

        when(accountInformationService.createConsent(any(), any())).thenReturn(response);

        GeneralResponse<ConsentCreationResponse> consentResponse = service.createConsent(RequestHeaders.fromMap(Collections.emptyMap()), new Consents());

        verify(accountInformationService, times(1)).createConsent(any(), any());

        assertThat(consentResponse).isEqualTo(response);
    }
}