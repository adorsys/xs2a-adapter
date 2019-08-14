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

import de.adorsys.xs2a.adapter.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.model.AspspTO;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Profile("dev")
@RestController
public class AspspController {
    static final String ASPSP_ID = "{aspspId}";
    static final String V1_ASPSP_BY_ID = AspspService.V1_APSPS + "/" + ASPSP_ID;
    private final AspspRepository aspspRepository;
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public AspspController(AspspRepository aspspRepository) {
        this.aspspRepository = aspspRepository;
    }

    @PostMapping(AspspService.V1_APSPS)
    ResponseEntity<AspspTO> create(@RequestBody AspspTO to) {
        Aspsp aspsp = aspspRepository.save(aspspMapper.toAspsp(to));
        String uri = V1_ASPSP_BY_ID.replace(ASPSP_ID, aspsp.getId());
        return ResponseEntity.created(URI.create(uri)).body(aspspMapper.toAspspTO(aspsp));
    }

    @PutMapping(AspspService.V1_APSPS)
    ResponseEntity<AspspTO> update(@RequestBody AspspTO aspsp) {
        aspspRepository.save(aspspMapper.toAspsp(aspsp));
        return ResponseEntity.ok(aspsp);
    }

    @DeleteMapping(V1_ASPSP_BY_ID)
    ResponseEntity<Void> deleteById(@PathVariable("aspspId") String aspspId) {
        aspspRepository.deleteById(aspspId);
        return ResponseEntity.noContent().build();
    }
}
