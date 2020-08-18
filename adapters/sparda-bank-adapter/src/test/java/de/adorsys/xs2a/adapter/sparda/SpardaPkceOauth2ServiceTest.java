package de.adorsys.xs2a.adapter.sparda;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class SpardaPkceOauth2ServiceTest {
    @Test
    void codeVerifierBounds() {
        String codeVerifier = new SpardaPkceOauth2Service(null).codeVerifier();
        assertThat(codeVerifier).hasSizeBetween(44, 127);
    }
}
