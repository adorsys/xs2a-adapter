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

import de.adorsys.xs2a.adapter.rest.api.model.AspspTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AspspSearchApi {

    String V1_APSPS = "/v1/aspsps";
    String V1_APSPS_BY_ID = V1_APSPS + "/{aspsp-id}";

    @RequestMapping(value = V1_APSPS, method = RequestMethod.GET)
    ResponseEntity<List<AspspTO>> getAspsps(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "bic", required = false) String bic,
                                            @RequestParam(value = "bankCode", required = false) String bankCode,
                                            @RequestParam(value = "iban", required = false) String iban, // if present - other params ignored
                                            @RequestParam(value = "after", required = false) String after,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size);

    @RequestMapping(value = V1_APSPS_BY_ID, method = RequestMethod.GET)
    ResponseEntity<AspspTO> getById(@PathVariable("aspsp-id") String id);
}
