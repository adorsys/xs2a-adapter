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

import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AspspServiceImpl extends AspspSearchServiceImpl implements AspspRepository {

    private final Logger log = LoggerFactory.getLogger(AspspServiceImpl.class);
    private final AspspRepository luceneAspspRepository;

    public AspspServiceImpl(AspspRepository luceneAspspRepository) {
        super(luceneAspspRepository);
        this.luceneAspspRepository = luceneAspspRepository;
    }

    @Override
    public Aspsp save(Aspsp aspsp) {
        log.debug("Save aspsp: {}", aspsp);
        return luceneAspspRepository.save(aspsp);
    }

    @Override
    public void deleteById(String aspspId) {
        log.debug("Delete aspsp by id: {}", aspspId);
        luceneAspspRepository.deleteById(aspspId);
    }
}
