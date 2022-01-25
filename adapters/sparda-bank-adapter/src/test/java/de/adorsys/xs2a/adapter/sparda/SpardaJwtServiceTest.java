/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.sparda;

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
