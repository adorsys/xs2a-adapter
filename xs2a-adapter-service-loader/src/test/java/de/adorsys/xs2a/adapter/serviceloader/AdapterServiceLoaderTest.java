package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.api.exception.AspspRegistrationNotFoundException;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.*;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdapterServiceLoaderTest {

    private static final String ADAPTER_ID = "test-adapter";
    private static final String ASPSP_ID = "test-aspsp-id";
    private static final String BANK_CODE = "test-bank-code";
    private static final String BIC = "test-bic";

    private static RequestHeaders requestHeadersWithAspspId;
    private static RequestHeaders requestHeadersWithBankCode;
    private static RequestHeaders requestHeadersWithBic;
    private static RequestHeaders requestHeadersWithBankCodeAndBic;

    private final AspspReadOnlyRepository aspspRepository = mock(AspspReadOnlyRepository.class);
    private AdapterServiceLoader adapterServiceLoader = new AdapterServiceLoader(aspspRepository, null, null, null, null, false);

    @BeforeEach
    public void setUp() {
        requestHeadersWithAspspId = fromMap(singletonMap(X_GTW_ASPSP_ID, ASPSP_ID));
        requestHeadersWithBankCode = fromMap(singletonMap(X_GTW_BANK_CODE, BANK_CODE));
        requestHeadersWithBic = fromMap(singletonMap(X_GTW_BIC, BIC));

        Map<String, String> headers = new HashMap<>();
        headers.put(X_GTW_BANK_CODE, BANK_CODE);
        headers.put(X_GTW_BIC, BIC);

        requestHeadersWithBankCodeAndBic = fromMap(headers);
    }

    @Test
    void getServiceProvider() {
        Optional<AccountInformationServiceProvider> accountInformationServiceProvider =
            adapterServiceLoader.getServiceProvider(AccountInformationServiceProvider.class, ADAPTER_ID);
        assertThat(accountInformationServiceProvider).get().hasFieldOrPropertyWithValue("adapterId", ADAPTER_ID);
    }

    @Test
    void getServiceProviderIsCaseInsensitive() {
        Optional<AccountInformationServiceProvider> accountInformationServiceProvider =
            adapterServiceLoader.getServiceProvider(AccountInformationServiceProvider.class, ADAPTER_ID.toUpperCase());
        assertThat(accountInformationServiceProvider).get().hasFieldOrPropertyWithValue("adapterId", ADAPTER_ID);
    }

    @Test
    void getAccountInformationServiceFindsAdapterByAspspId() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(aspsp));
        adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId);
        verify(aspspRepository, times(1)).findById(ASPSP_ID);
    }

    @Test
    void getAccountInformationServiceThrowsWhenAspspNotFound() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId)
        );
    }

    @Test
    void getServiceProviderThrowsExceptionWhenAdapterNotFound() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(new Aspsp()));

        Assertions.assertThrows(
            AdapterNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId)
        );
    }

    @Test
    void getAccountInformationServiceThrowsIfNothingFoundByBankCode() {
        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCode)
        );
    }

    @Test
    void getAccountInformationServiceThrowsIfNothingFoundByBic() {
        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithBic)
        );
    }

    @Test
    void getAccountInformationServiceThrowsIfNothingFoundByBankCodeAndBic() {
        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCodeAndBic)
        );
    }

    @Test
    void getAccountInformationServiceFindsAdapterByBankCode() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        when(aspspRepository.findByBankCode(BANK_CODE))
            .thenReturn(Collections.singletonList(aspsp));
        AccountInformationService ais = adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCode);
        assertThat(ais).isNotNull();
        verify(aspspRepository, times(1)).findByBankCode(BANK_CODE);
    }

    @Test
    void getAccountInformationServiceFindsAdapterByIban() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        String iban = "DE91100000000123456789";
        when(aspspRepository.findByIban(iban))
            .thenReturn(Collections.singletonList(aspsp));
        RequestHeaders requestHeaders = fromMap(singletonMap(X_GTW_IBAN, iban));
        AccountInformationService ais = adapterServiceLoader.getAccountInformationService(requestHeaders);
        assertThat(ais).isNotNull();
        verify(aspspRepository, times(1)).findByIban(iban);
    }

    @Test
    void getAccountInformationServiceFindsAdapterByBic() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        when(aspspRepository.findByBic(BIC))
            .thenReturn(Collections.singletonList(aspsp));
        AccountInformationService ais = adapterServiceLoader.getAccountInformationService(requestHeadersWithBic);
        assertThat(ais).isNotNull();
        verify(aspspRepository, times(1)).findByBic(BIC);
    }

    @Test
    void getAccountInformationServiceFindsAdapterByBankCodeAndBic() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);

        when(aspspRepository.findLike(buildAspsp(BANK_CODE, BIC, null)))
            .thenReturn(Collections.singletonList(aspsp));
        AccountInformationService ais = adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCodeAndBic);
        assertThat(ais).isNotNull();
        verify(aspspRepository, times(1)).findLike(buildAspsp(BANK_CODE, BIC, null));
    }

    @Test
    void getAccountInformationServicePrefersSearchingByAspspId() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(aspsp));

        HashMap<String, String> requestHeadersMap = new HashMap<>(2);
        requestHeadersMap.put(X_GTW_ASPSP_ID, ASPSP_ID);
        requestHeadersMap.put(X_GTW_BANK_CODE, BANK_CODE);

        adapterServiceLoader.getAccountInformationService(RequestHeaders.fromMap(requestHeadersMap));
        verify(aspspRepository, times(1)).findById(ASPSP_ID);
    }

    @Test
    void getAccountInformationServiceThrowsIfMoreThanOneAspspFoundByBankCode() {
        String file = getClass().getResource("/external.adapter.config.properties").getFile();
        System.setProperty("adapter.config.file.path", file);
        AdapterConfig.reload();

        when(aspspRepository.findByBankCode(BANK_CODE))
            .thenReturn(Arrays.asList(new Aspsp(), new Aspsp()));

        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCode)
        );
    }

    @Test
    void getAccountInformationServiceReturnFirst() {
        adapterServiceLoader = new AdapterServiceLoader(aspspRepository, null, null, null, null, true);
        Aspsp aspsp1 = new Aspsp();
        aspsp1.setId("1");

        Aspsp aspsp2 = new Aspsp();
        aspsp2.setId("2");

        when(aspspRepository.findByBankCode(BANK_CODE))
            .thenReturn(Arrays.asList(aspsp1, aspsp2));

        Aspsp aspsp = adapterServiceLoader.getAspsp(requestHeadersWithBankCode);

        assertThat(aspsp).isSameAs(aspsp1);
    }

    @Test
    void getAccountInformationServiceThrowsIfMoreThanOneAspspFoundByBic() {
        when(aspspRepository.findByBic(BIC))
            .thenReturn(Arrays.asList(new Aspsp(), new Aspsp()));

        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithBic)
        );
    }

    @Test
    void getAccountInformationServiceThrowsIfMoreThanOneAspspFoundByBankCodeAndBic() {
        when(aspspRepository.findLike(buildAspsp(BANK_CODE, BIC, null)))
            .thenReturn(Arrays.asList(new Aspsp(), new Aspsp()));

        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCodeAndBic)
        );
    }

    @Test
    void getAccountInformationServiceThrowsIfNotAspspIdentifyingHeadersProvided() {
        RequestHeaders requestHeaders = empty();
        Assertions.assertThrows(
            AspspRegistrationNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeaders)
        );
    }

    @Test
    void getAccountInformationServiceThrowsAdapterNotFoundException() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(new Aspsp()));

        Assertions.assertThrows(AdapterNotFoundException.class,
            () -> adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId)
        );
    }

    @Test
    void getPaymentInitiationService() {
        Aspsp aspsp = buildAspsp(null, null, ADAPTER_ID);

        when(aspspRepository.findById(anyString()))
            .thenReturn(Optional.of(aspsp));

        PaymentInitiationService actualService = adapterServiceLoader.getPaymentInitiationService(requestHeadersWithAspspId);

        verify(aspspRepository, times(1)).findById(anyString());

        assertThat(actualService).isInstanceOf(TestPaymentInitiationService.class);
    }

    @Test
    void getOauth2Service() {
        Aspsp aspsp = buildAspsp(null, null, ADAPTER_ID);

        when(aspspRepository.findById(anyString()))
            .thenReturn(Optional.of(aspsp));

        Oauth2Service actualService = adapterServiceLoader.getOauth2Service(requestHeadersWithAspspId);

        verify(aspspRepository, times(1)).findById(anyString());

        assertThat(actualService).isInstanceOf(TestOauth2Service.class);
    }

    @Test
    void getDownloadService() {
        Aspsp aspsp = buildAspsp(null, null, ADAPTER_ID);

        when(aspspRepository.findById(anyString()))
            .thenReturn(Optional.of(aspsp));

        DownloadService actualService = adapterServiceLoader.getDownloadService(requestHeadersWithAspspId);

        verify(aspspRepository, times(1)).findById(anyString());

        assertThat(actualService).isInstanceOf(TestDownloadService.class);
    }

    @Test
    void getEmbeddedPreAuthorisationService() {
        Aspsp aspsp = buildAspsp(null, null, ADAPTER_ID);

        when(aspspRepository.findById(anyString()))
            .thenReturn(Optional.of(aspsp));

        EmbeddedPreAuthorisationService actualService = adapterServiceLoader.getEmbeddedPreAuthorisationService(requestHeadersWithAspspId);

        verify(aspspRepository, times(1)).findById(anyString());

        assertThat(actualService).isInstanceOf(TestEmbeddedPreAuthorisationService.class);
    }

    private Aspsp buildAspsp(String bankCode, String bic, String adapterId) {
        Aspsp aspsp = new Aspsp();
        aspsp.setBankCode(bankCode);
        aspsp.setBic(bic);
        aspsp.setAdapterId(adapterId);
        return aspsp;
    }
}
