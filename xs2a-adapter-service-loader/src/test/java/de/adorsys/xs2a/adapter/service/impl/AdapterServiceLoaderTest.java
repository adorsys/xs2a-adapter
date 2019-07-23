package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.exception.AdapterMappingNotFoundException;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AdapterServiceLoaderTest {

    private static final String ADAPTER_ID = "test-adapter";
    private static final String ASPSP_ID = "test-aspsp-id";

    private final AspspRepository aspspRepository = mock(AspspRepository.class);
    private final AdapterServiceLoader adapterServiceLoader = new AdapterServiceLoader(aspspRepository);

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
        adapterServiceLoader.getAccountInformationService(ASPSP_ID);
        verify(aspspRepository, times(1)).findById(ASPSP_ID);
    }

    @Test(expected = AdapterMappingNotFoundException.class)
    public void getAccountInformationServiceThrowsWhenAspspNotFound() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.empty());
        adapterServiceLoader.getAccountInformationService(ASPSP_ID);
    }

    @Test(expected = AdapterNotFoundException.class)
    public void getServiceProviderThrowsExceptionWhenAdapterNotFound() {
        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(new Aspsp()));
        adapterServiceLoader.getAccountInformationService(ASPSP_ID);
    }
}
