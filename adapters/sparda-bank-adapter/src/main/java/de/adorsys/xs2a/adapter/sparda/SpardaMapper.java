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

package de.adorsys.xs2a.adapter.sparda;

import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.Transactions;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.sparda.model.SpardaOK200TransactionDetails;
import de.adorsys.xs2a.adapter.sparda.model.SpardaTransactionDetails;
import de.adorsys.xs2a.adapter.sparda.model.SpardaTransactionResponse200Json;
import org.mapstruct.Mapper;

@Mapper
public interface SpardaMapper {
    TransactionsResponse200Json toTransactionsResponse200Json(SpardaTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(SpardaOK200TransactionDetails value);
    Transactions toTransactions(SpardaTransactionDetails value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
