package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Psd2AdapterServiceLoaderTest {
    private static final String ASPSP_ID = "ASPSP_ID";
    private static final String BANK_CODE = "BANK_CODE";

    private Psd2AdapterServiceLoader psd2AdapterServiceLoader;
    private RequestHeaders requestHeadersWithAspspId;
    private RequestHeaders requestHeadersWithBankCode;

    @BeforeEach
    public void setUp() {
        AspspReadOnlyRepository aspspRepository = mock(AspspReadOnlyRepository.class);

        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId("test-adapter");

        when(aspspRepository.findById(ASPSP_ID))
            .thenReturn(Optional.of(aspsp));

        when(aspspRepository.findByBankCode(BANK_CODE))
            .thenReturn(singletonList(aspsp));

        psd2AdapterServiceLoader = new Psd2AdapterServiceLoader(aspspRepository, null, null, false);
        requestHeadersWithAspspId = RequestHeaders.fromMap(singletonMap(RequestHeaders.X_GTW_ASPSP_ID, ASPSP_ID));
        requestHeadersWithBankCode = RequestHeaders.fromMap(singletonMap(RequestHeaders.X_GTW_BANK_CODE, BANK_CODE));
    }

    @Test
    public void shouldLoadXs2aAdapterByAspspIdWhenPsd2AdapterNotFound() {

        Psd2AccountInformationService psd2AccountInformationService =
            psd2AdapterServiceLoader.getPsd2AccountInformationService(requestHeadersWithAspspId);

        assertThat(psd2AccountInformationService).isNotNull();
    }

    @Test
    public void shouldLoadXs2aAdapterByBankCodeIdWhenPsd2AdapterNotFound() {

        Psd2AccountInformationService psd2AccountInformationService =
            psd2AdapterServiceLoader.getPsd2AccountInformationService(requestHeadersWithBankCode);

        assertThat(psd2AccountInformationService).isNotNull();
    }
}
