package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.ais.Consents;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ConsentServiceImplTest {

    @Test
    public void createConsent() {
        ConsentCreationResponse response = new ConsentCreationResponse();
        DeutscheBankAccountInformationService deutscheBankConsentService = mock(DeutscheBankAccountInformationService.class);
        AccountInformationServiceImpl service = new AccountInformationServiceImpl(){
            @Override
            AccountInformationService getConsentService(Headers headers) {
                return deutscheBankConsentService;
            }
        };

        when(deutscheBankConsentService.createConsent(any(), any())).thenReturn(response);

        ConsentCreationResponse consent = service.createConsent(new Consents(), Headers.builder().build());

        verify(deutscheBankConsentService, times(1)).createConsent(any(), any());

        assertThat(consent).isEqualTo(response);
    }
}