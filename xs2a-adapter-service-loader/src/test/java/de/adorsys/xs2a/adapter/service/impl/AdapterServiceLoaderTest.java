package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.exception.AdapterMappingNotFoundException;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AdapterServiceLoaderTest {

    private static final String ADAPTER_ID = "test-adapter";
    private static final String BIC = "TEST BIC";

    private final AspspAdapterConfig aspspAdapterConfig = mock(AspspAdapterConfig.class);
    private final AdapterServiceLoader adapterServiceLoader = new AdapterServiceLoader(aspspAdapterConfig);

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
    public void getAccountInformationServiceResolvesBicToAdapterId() {
        AspspAdapterConfigRecord configRecord = new AspspAdapterConfigRecord();
        configRecord.setAdapterId(ADAPTER_ID);
        when(aspspAdapterConfig.getAspspAdapterConfigRecord(BIC))
            .thenReturn(Optional.of(configRecord));
        adapterServiceLoader.getAccountInformationService(BIC);
        verify(aspspAdapterConfig, times(1)).getAspspAdapterConfigRecord(BIC);
    }

    @Test(expected = AdapterMappingNotFoundException.class)
    public void getAccountInformationServiceThrowsWhenConfigRecordNotFound() {
        when(aspspAdapterConfig.getAspspAdapterConfigRecord(BIC))
            .thenReturn(Optional.empty());
        adapterServiceLoader.getAccountInformationService(BIC);
    }

    @Test(expected = AdapterNotFoundException.class)
    public void getServiceProviderThrowsExceptionWhenAdapterNotFound() {
        when(aspspAdapterConfig.getAspspAdapterConfigRecord(BIC))
            .thenReturn(Optional.of(new AspspAdapterConfigRecord()));
        adapterServiceLoader.getAccountInformationService(BIC);
    }

    @Test
    public void getSupportedAspspsReturnsConfigEntriesWithServiceProviders() {

        AspspAdapterConfig configStub = new AspspAdapterConfig() {
            List<AspspAdapterConfigRecord> records = new ArrayList<>(2);
            {
                records.add(newAspspAdapterConfigRecord(BIC, ADAPTER_ID));
                records.add(new AspspAdapterConfigRecord());
            }
            @Override
            public Optional<AspspAdapterConfigRecord> getAspspAdapterConfigRecord(String bic) {
                return records.stream()
                    .filter(r -> bic.equals(r.getBic()))
                    .findFirst();
            }

            @Override
            public Iterator<AspspAdapterConfigRecord> iterator() {
                return records.iterator();
            }
        };
        AdapterServiceLoader adapterServiceLoader = new AdapterServiceLoader(configStub);

        assertThat(adapterServiceLoader.getSupportedAspsps()).hasSize(1);
    }

    private AspspAdapterConfigRecord newAspspAdapterConfigRecord(String bic, String adapterId) {
        AspspAdapterConfigRecord record = new AspspAdapterConfigRecord();
        record.setBic(bic);
        record.setAdapterId(adapterId);
        return record;
    }
}
