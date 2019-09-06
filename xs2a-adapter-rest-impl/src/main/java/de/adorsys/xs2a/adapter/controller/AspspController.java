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

package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.api.AspspSearchApi;
import de.adorsys.xs2a.adapter.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.model.AspspTO;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Profile("dev")
@RestController
public class AspspController {
    static final String ASPSP_ID = "{aspspId}";
    static final String V1_ASPSP_BY_ID = AspspSearchApi.V1_APSPS + "/" + ASPSP_ID;
    static final String V1_ASPSP_EXPORT = AspspSearchApi.V1_APSPS + "/export";
    private final AspspRepository aspspRepository;
    private final AspspCsvService aspspCsvService;
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public AspspController(AspspRepository aspspRepository, AspspCsvService aspspCsvService) {
        this.aspspRepository = aspspRepository;
        this.aspspCsvService = aspspCsvService;
    }

    @PostMapping(AspspSearchApi.V1_APSPS)
    ResponseEntity<AspspTO> create(@RequestBody AspspTO to) {
        Aspsp aspsp = aspspRepository.save(aspspMapper.toAspsp(to));
        String uri = V1_ASPSP_BY_ID.replace(ASPSP_ID, aspsp.getId());
        return ResponseEntity.created(URI.create(uri)).body(aspspMapper.toAspspTO(aspsp));
    }

    @PutMapping(AspspSearchApi.V1_APSPS)
    ResponseEntity<AspspTO> update(@RequestBody AspspTO aspsp) {
        aspspRepository.save(aspspMapper.toAspsp(aspsp));
        return ResponseEntity.ok(aspsp);
    }

    @DeleteMapping(V1_ASPSP_BY_ID)
    ResponseEntity<Void> deleteById(@PathVariable("aspspId") String aspspId) {
        aspspRepository.deleteById(aspspId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = V1_ASPSP_EXPORT, produces = "text/csv")
    public ResponseEntity<byte[]> export() {
        byte[] response = aspspCsvService.exportCsv();
        String fileName = "aspsps.csv";

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentLength(response.length);
        responseHeaders.setContentType(new MediaType("text", "csv"));
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
}
