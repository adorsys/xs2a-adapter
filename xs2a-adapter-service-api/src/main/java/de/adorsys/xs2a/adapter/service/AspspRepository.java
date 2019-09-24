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

package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.List;

public interface AspspRepository extends AspspReadOnlyRepository {

    Aspsp save(Aspsp aspsp);

    /**
     * Saves a list of aspsps into existing Lucene indexes
     * <p>
     * Writes all Aspsp objects into the current Lucene repository. The iteration through input
     * collection of aspsps is taken place, each object is either added or updated, if it
     * already exists within the repository.
     *
     * @param aspsps a list of aspsp objects to be added into the repository
     */
    void saveAll(List<Aspsp> aspsps);

    void deleteById(String aspspId);

    /**
     * Deletes all records from the existing Lucene indexes
     */
    void deleteAll();
}
