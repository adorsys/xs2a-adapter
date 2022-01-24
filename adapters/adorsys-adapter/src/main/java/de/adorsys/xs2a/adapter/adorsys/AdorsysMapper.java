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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.adorsys.model.AdorsysOK200TransactionDetails;
import de.adorsys.xs2a.adapter.adorsys.model.AdorsysTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdorsysMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(AdorsysTransactionsResponse200Json value);

    @Mapping(target = "transactionsDetails", source = "transactionsDetails.transactionDetails")
    OK200TransactionDetails toOK200TransactionDetails(AdorsysOK200TransactionDetails value);
}
