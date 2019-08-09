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
import de.adorsys.xs2a.adapter.service.AspspModifyRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Profile("dev")
@RestController
public class AspspModifyController {
    private static final String ASPSP_ID = "{aspspId}";
    private static final String V1_ASPSP_BY_ID = AspspService.V1_APSPS + "/" + ASPSP_ID;
    private final AspspModifyRepository aspspModifyRepository;
    private final AspspMapper aspspMapper = Mappers.getMapper(AspspMapper.class);

    public AspspModifyController(AspspModifyRepository aspspModifyRepository) {
        this.aspspModifyRepository = aspspModifyRepository;
    }

    @PostMapping(AspspService.V1_APSPS)
    ResponseEntity<AspspTO> addAspsp(@RequestBody AspspTO to) {
        Aspsp aspsp = aspspModifyRepository.create(aspspMapper.toAspsp(to));
        String uri = V1_ASPSP_BY_ID.replace(ASPSP_ID, aspsp.getId());
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PutMapping(AspspService.V1_APSPS)
    ResponseEntity<AspspTO> updateAspsp(@RequestBody AspspTO aspsp){
        aspspModifyRepository.update(aspspMapper.toAspsp(aspsp));
        return ResponseEntity.ok(aspsp);
    }

    @DeleteMapping(V1_ASPSP_BY_ID)
    ResponseEntity<Void> delete(@PathVariable("aspspId") String aspspId){
        aspspModifyRepository.remove(aspspId);
        return ResponseEntity.noContent().build();
    }
}
