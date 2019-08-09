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

package de.adorsys.xs2a.adapter.registry;

import de.adorsys.xs2a.adapter.service.AspspModifyRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class AspspModifyServiceImpl implements AspspModifyRepository {

    private final Logger log = LoggerFactory.getLogger(AspspModifyServiceImpl.class);
    private final AspspModifyRepository luceneAspspRepository;

    public AspspModifyServiceImpl(AspspModifyRepository luceneAspspRepository) {
        this.luceneAspspRepository = luceneAspspRepository;
    }

    @Override
    public Aspsp create(Aspsp aspsp) {
        if (aspsp.getId() == null) {
            aspsp.setId(UUID.randomUUID().toString());
        }
        log.debug("Save aspsp: {}", aspsp);
        return luceneAspspRepository.create(aspsp);
    }

    @Override
    public void update(Aspsp aspsp) {
        log.debug("Update aspsp: {}", aspsp);
        luceneAspspRepository.update(aspsp);
    }


    @Override
    public void remove(String aspspId) {
        log.debug("Delete aspsp by id: {}", aspspId);
        luceneAspspRepository.remove(aspspId);
    }
}
