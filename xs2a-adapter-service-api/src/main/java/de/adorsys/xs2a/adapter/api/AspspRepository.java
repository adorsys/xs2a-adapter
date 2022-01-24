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

package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.model.Aspsp;

import java.util.List;

public interface AspspRepository extends AspspReadOnlyRepository {

    Aspsp save(Aspsp aspsp);

    /**
     * Saves a list of aspsps into existing Lucene indexes.
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
     * Deletes all records from the existing Lucene indexes.
     */
    void deleteAll();
}
