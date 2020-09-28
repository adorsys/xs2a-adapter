package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.http.RequestSigningInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.DATE;
import static de.adorsys.xs2a.adapter.api.RequestHeaders.TPP_SIGNATURE_CERTIFICATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdorsysSigningHeadersInterceptorTest {

    private final RequestSigningInterceptor signingInterceptor = mock(RequestSigningInterceptor.class);
    private final HttpClient client = mock(HttpClient.class);
    private final Request.Builder builder = new RequestBuilderImpl(client, null, null);
    private AdorsysSigningHeadersInterceptor adorsysSigningHeadersInterceptor;

    @Test
    void preHandle_requestSigningEnabled() {
        String file = getClass().getResource(File.separator + "adorsys.adapter.config.signing-enabled.properties").getFile();
        AdapterConfig.setConfigFile(file);
        adorsysSigningHeadersInterceptor = new AdorsysSigningHeadersInterceptor(signingInterceptor);
        when(signingInterceptor.preHandle(any(Request.Builder.class)))
            .thenReturn(builder.header(TPP_SIGNATURE_CERTIFICATE, "certificate"));

        Request.Builder actualBuilder = adorsysSigningHeadersInterceptor.preHandle(builder);

        verify(signingInterceptor, times(1)).preHandle(any(Request.Builder.class));

        Assertions.assertThat(actualBuilder.headers())
            .isNotEmpty()
            .containsKeys(DATE, TPP_SIGNATURE_CERTIFICATE)
            .containsValue("-----BEGIN CERTIFICATE-----certificate-----END CERTIFICATE-----");
    }

    @Test
    void preHandle_requestSigningDisabled() {
        String file = getClass().getResource(File.separator + "adorsys.adapter.config.signing-disabled.properties").getFile();
        AdapterConfig.setConfigFile(file);
        adorsysSigningHeadersInterceptor = new AdorsysSigningHeadersInterceptor(signingInterceptor);

        adorsysSigningHeadersInterceptor.preHandle(builder);

        verifyNoInteractions(signingInterceptor);
    }
}
