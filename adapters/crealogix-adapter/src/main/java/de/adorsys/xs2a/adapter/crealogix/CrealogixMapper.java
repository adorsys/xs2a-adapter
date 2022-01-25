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

package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.*;
import org.mapstruct.Mapper;

@Mapper
public interface CrealogixMapper {

    PaymentInitiationWithStatusResponse toPaymentInitiationWithStatusResponse(CrealogixPaymentInitiationWithStatusResponse value);
    TransactionsResponse200Json toTransactionsResponse200Json(CrealogixTransactionResponse200Json value);
    OK200TransactionDetails toOK200TransactionDetails(CrealogixOK200TransactionDetails value);
    Transactions toTransactions(CrealogixTransactionDetails value);
    TokenResponse toTokenResponse(CrealogixValidationResponse value);

    default String map(RemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }
}
