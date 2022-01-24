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

package de.adorsys.xs2a.adapter.rest.api;

import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

public interface Oauth2Api {

    String AUTHORIZATION_REQUEST_URI = "/oauth2/authorization-request-uri";

    @GetMapping(AUTHORIZATION_REQUEST_URI)
    HrefType getAuthorizationUrl(@RequestHeader Map<String, String> headers,
                                 @RequestParam Map<String, String> parameters) throws IOException;

    @PostMapping("/oauth2/token")
    TokenResponseTO getToken(@RequestHeader Map<String, String> headers,
                             @RequestParam Map<String, String> parameters) throws IOException;
}
