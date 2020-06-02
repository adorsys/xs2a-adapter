package de.adorsys.xs2a.adapter.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpardaJwtServiceTest {

    /*
     * Token content:
     *
     * 1. Headers:
     *    {
     *      "alg": "HS256",
     *      "typ": "JWT"
     *    }
     *
     * 2. Payload:
     *    {
     *      "sub": "1234567890",
     *      "name": "John Doe",
     *      "iat": 1516239022
     *    }
     */
    private static final String CORRECT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                                                    ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
                                                    ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String WRONG_TOKEN = "wrongToken";

    private static final String EXPECTED_PSU_ID = "1234567890";

    private final SpardaJwtService spardaJwtService = new SpardaJwtService();

    @Test
    void getPsuId_failure_ParseExceptionIsThrown() {
        assertThrows(RuntimeException.class, () -> spardaJwtService.getPsuId(WRONG_TOKEN));
    }

    @Test
    void getPsuId_success() {
        String actualPsuId = spardaJwtService.getPsuId(CORRECT_TOKEN);
        assertEquals(EXPECTED_PSU_ID, actualPsuId);
    }
}
