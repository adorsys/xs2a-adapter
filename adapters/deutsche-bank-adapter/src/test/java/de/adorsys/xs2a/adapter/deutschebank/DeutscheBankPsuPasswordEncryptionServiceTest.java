package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.PsuPasswordEncryptionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DeutscheBankPsuPasswordEncryptionServiceTest {

    @Test
    void getInstance_shouldNotThrowAnError() {
        PsuPasswordEncryptionService encryptionService = new DeutscheBankPsuPasswordEncryptionService();

        Assertions.assertThat(encryptionService.encrypt("foo"))
            .isNotNull();
    }
}
