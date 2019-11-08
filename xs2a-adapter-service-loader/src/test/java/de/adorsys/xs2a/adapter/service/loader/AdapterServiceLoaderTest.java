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

import java.util.*;

import static de.adorsys.xs2a.adapter.service.RequestHeaders.*;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AdapterServiceLoaderTest {

    private static final String ADAPTER_ID = "test-adapter";
    private static final String ASPSP_ID = "test-aspsp-id";
    private static final String BANK_CODE = "test-bank-code";
    private static final String BIC = "test-bic";

    private static RequestHeaders requestHeadersWithAspspId;
    private static RequestHeaders requestHeadersWithBankCode;
    private static RequestHeaders requestHeadersWithBic;
    private static RequestHeaders requestHeadersWithBankCodeAndBic;

    private final AspspReadOnlyRepository aspspRepository = mock(AspspReadOnlyRepository.class);
    private final AdapterServiceLoader adapterServiceLoader = new AdapterServiceLoader(aspspRepository, null, null);

    @Before
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

    @Test(expected = AspspRegistrationNotFoundException.class)
    public void getAccountInformationServiceThrowsIfNothingFoundByBic() {
        adapterServiceLoader.getAccountInformationService(requestHeadersWithBic);
    }

    @Test(expected = AspspRegistrationNotFoundException.class)
    public void getAccountInformationServiceThrowsIfNothingFoundByBankCodeAndBic() {
        adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCodeAndBic);
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
    public void getAccountInformationServiceFindsAdapterByBic() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);
        when(aspspRepository.findByBic(BIC))
            .thenReturn(Collections.singletonList(aspsp));
        AccountInformationService ais = adapterServiceLoader.getAccountInformationService(requestHeadersWithBic);
        assertThat(ais).isNotNull();
        verify(aspspRepository, times(1)).findByBic(BIC);
    }

    @Test
    public void getAccountInformationServiceFindsAdapterByBankCodeAndBic() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId(ADAPTER_ID);

        when(aspspRepository.findLike(buildAspsp(BANK_CODE, BIC)))
            .thenReturn(Collections.singletonList(aspsp));
        AccountInformationService ais = adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCodeAndBic);
        assertThat(ais).isNotNull();
        verify(aspspRepository, times(1)).findLike(buildAspsp(BANK_CODE, BIC));
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
    public void getAccountInformationServiceThrowsIfMoreThanOneAspspFoundByBic() {
        when(aspspRepository.findByBic(BIC))
            .thenReturn(Arrays.asList(new Aspsp(), new Aspsp()));
        adapterServiceLoader.getAccountInformationService(requestHeadersWithBic);
    }

    @Test(expected = AspspRegistrationNotFoundException.class)
    public void getAccountInformationServiceThrowsIfMoreThanOneAspspFoundByBankCodeAndBic() {
        when(aspspRepository.findLike(buildAspsp(BANK_CODE, BIC)))
            .thenReturn(Arrays.asList(new Aspsp(), new Aspsp()));
        adapterServiceLoader.getAccountInformationService(requestHeadersWithBankCodeAndBic);
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

    private Aspsp buildAspsp(String bankCode, String bic) {
        Aspsp aspsp = new Aspsp();
        aspsp.setBankCode(bankCode);
        aspsp.setBic(bic);
        return aspsp;
    }
}
