/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.model.AspspTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AspspSearchApi {

    String V1_APSPS = "/v1/aspsps";

    @RequestMapping(value = V1_APSPS, method = RequestMethod.GET)
    ResponseEntity<List<AspspTO>> getAspsps(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "bic", required = false) String bic,
                                            @RequestParam(value = "bankCode", required = false) String bankCode,
                                            @RequestParam(value = "iban", required = false) String iban, // if present - other params ignored
                                            @RequestParam(value = "after", required = false) String after,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size);
}
