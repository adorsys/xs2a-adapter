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

package de.adorsys.xs2a.adapter.impl.signing.algorithm;

import java.util.Base64;

public enum EncodingAlgorithm {
    BASE64("BASE64", new Base64EncodingService());

    private final String algorithmName;
    private final EncodingService encodingService;

    EncodingAlgorithm(String algorithmName, EncodingService encodingService) {
        this.algorithmName = algorithmName;
        this.encodingService = encodingService;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public EncodingService getEncodingService() {
        return encodingService;
    }

    public interface EncodingService {

        String encode(byte[] data);
    }

    private static class Base64EncodingService implements EncodingService {

        @Override
        public String encode(byte[] data) {
            return Base64.getEncoder().encodeToString(data);
        }
    }
}
