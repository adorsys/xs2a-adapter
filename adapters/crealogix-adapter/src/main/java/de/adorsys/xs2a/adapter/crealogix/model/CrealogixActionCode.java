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

package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.stream.Stream;

public enum CrealogixActionCode {
    PROMPT_FOR_LOGIN_NAME_PASSWORD,
    PROMPT_FOR_AUTH_METHOD_SELECTION,
    PROMPT_FOR_TAN,
    POLL_FOR_TAN,
    STOP_POLL_FOR_TAN,
    PROMPT_FOR_PASSWORD_CHANGE,
    PROMPT_FOR_SCOPE_ACCEPTANCE,
    SEND_REQUEST;

    public static CrealogixActionCode fromValue(String text) {
        return Stream.of(CrealogixActionCode.values())
            .filter(c -> c.name().equalsIgnoreCase(text))
            .findFirst().orElse(null);
    }
}
