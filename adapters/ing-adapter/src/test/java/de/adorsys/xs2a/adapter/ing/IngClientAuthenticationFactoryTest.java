package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IngClientAuthenticationFactoryTest {

    @Test
    void getClientAuthenticationFactory() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        Pkcs12KeyStore keyStore = Mockito.mock(Pkcs12KeyStore.class);
        Mockito.when(keyStore.getQsealPrivateKey(Mockito.any())).thenThrow(new KeyStoreException());

        assertThatThrownBy(() -> IngClientAuthenticationFactory.getClientAuthenticationFactory(keyStore))
            .isInstanceOf(Xs2aAdapterException.class);
    }
}
