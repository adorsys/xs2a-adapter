package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.exception.AspspRegistrationNotFoundException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static de.adorsys.xs2a.adapter.service.RequestHeaders.*;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AdapterServiceLoaderTest {

    private static final String ADAPTER_ID = "test-adapter";
    private static final String ASPSP_ID = "test-aspsp-id";
    private static final String BANK_CODE = "test-bank-code";

    private static RequestHeaders requestHeadersWithAspspId;
    private static RequestHeaders requestHeadersWithBankCode;

    private final AspspReadOnlyRepository aspspRepository = mock(AspspReadOnlyRepository.class);
    private final AdapterServiceLoader adapterServiceLoader = new AdapterServiceLoader(aspspRepository, null, null);

    @Before
    public void setUp() {
        requestHeadersWithAspspId = fromMap(singletonMap(X_GTW_ASPSP_ID, ASPSP_ID));
        requestHeadersWithBankCode = fromMap(singletonMap(X_GTW_BANK_CODE, BANK_CODE));
    }

    @Test
    public void getServiceProvider() {
        Optional<AccountInformationServiceProvider> accountInformationServiceProvider =
            adapterServiceLoader.getServiceProvider(AccountInformationServiceProvider.class, ADAPTER_ID);
        assertThat(accountInformationServiceProvider).get().hasFieldOrPropertyWithValue("adapterId", ADAPTER_ID);
    }

    @Test
    public void getServiceProviderIsCaseInsensitive() {
        Optional<AccountInformationServiceProvider> accountInformationServiceProvider =
            adapterServiceLoader.getServiceProvider(AccountInformationServiceProvider.class, ADAPTER_ID.toUpperCase());
        assertThat(accountInformationServiceProvider).get().hasFieldOrPropertyWithValue("adapterId", ADAPTER_ID);
    }

    @Test
    public void getAccountInformationServiceFindsAdapterByAspspId() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(aspsp));
        adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId);
        verify(aspspRepository, times(1)).findById(ASPSP_ID);
    }

    @Test(expected = AspspRegistrationNotFoundException.class)
    public void getAccountInformationServiceThrowsWhenAspspNotFound() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.empty());
        adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId);
    }

    @Test(expected = AdapterNotFoundException.class)
    public void getServiceProviderThrowsExceptionWhenAdapterNotFound() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(new Aspsp()));
        adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId);
    }

    @Test(expected = AspspRegistrationNotFoundException.class)
    public void getAccountInformationServiceThrowsIfNothingFoundByBankCode() {
        adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCode);
    }

    @Test
    public void getAccountInformationServiceFindsAdapterByBankCode() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        when(aspspRepository.findByBankCode(BANK_CODE))
            .thenReturn(Collections.singletonList(aspsp));
        AccountInformationService ais = adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCode);
        assertThat(ais).isNotNull();
        verify(aspspRepository, times(1)).findByBankCode(BANK_CODE);
    }

    @Test
    public void getAccountInformationServicePrefersSearchingByAspspId() {
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

    @Test(expected = AspspRegistrationNotFoundException.class)
    public void getAccountInformationServiceThrowsIfMoreThanOneAspspFoundByBankCode() {
        when(aspspRepository.findByBankCode(BANK_CODE))
            .thenReturn(Arrays.asList(new Aspsp(), new Aspsp()));
        adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCode);
    }

    @Test(expected = AspspRegistrationNotFoundException.class)
    public void getAccountInformationServiceThrowsIfNotAspspIdentifyingHeadersProvided() {
        adapterServiceLoader.getAccountInformationService(RequestHeaders.fromMap(emptyMap()));
    }

    @Test(expected = AdapterNotFoundException.class)
    public void getAccountInformationServiceThrowsAdapterNotFoundException() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(new Aspsp()));

        adapterServiceLoader.getAccountInformationService(requestHeadersWithAspspId);
    }
}
