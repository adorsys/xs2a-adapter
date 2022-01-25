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

package de.adorsys.xs2a.tpp.controller;

import de.adorsys.xs2a.adapter.remote.client.AspspClient;
import de.adorsys.xs2a.adapter.rest.api.model.AspspTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AspspController {
    private final AspspClient aspspClient;

    public AspspController(AspspClient aspspClient) {
        this.aspspClient = aspspClient;
    }

    @RequestMapping("/v1/aspsps")
    ResponseEntity<List<AspspTO>> getAspsps(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String bic,
                                            @RequestParam(required = false) String bankCode,
                                            @RequestParam(required = false) String iban, // if present - other params ignored
                                            @RequestParam(required = false) String after,
                                            @RequestParam(required = false, defaultValue = "10") int size) {

        ResponseEntity<List<AspspTO>> aspsps = aspspClient.getAspsps(name, bic, bankCode, iban, after, size);

        return ResponseEntity.ok(aspsps.getBody());
    }
}
