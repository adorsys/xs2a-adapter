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
import java.util.Optional;

public interface AspspReadOnlyRepository {

    int DEFAULT_SIZE = 10;
    int MAX_SIZE = Integer.MAX_VALUE;

    Optional<Aspsp> findById(String id);

    default List<Aspsp> findByBic(String bic) {
        return findByBic(bic, DEFAULT_SIZE);
    }

    default List<Aspsp> findByBic(String bic, int size) {
        return findByBic(bic, null, size);
    }

    List<Aspsp> findByBic(String bic, String after, int size);

    default List<Aspsp> findByBankCode(String bankCode) {
        return findByBankCode(bankCode, DEFAULT_SIZE);
    }

    default List<Aspsp> findByBankCode(String bankCode, int size) {
        return findByBankCode(bankCode, null, size);
    }

    List<Aspsp> findByBankCode(String bankCode, String after, int size);

    default List<Aspsp> findByName(String name) {
        return findByName(name, DEFAULT_SIZE);
    }

    default List<Aspsp> findByName(String name, int size) {
        return findByName(name, null, size);
    }

    List<Aspsp> findByName(String name, String after, int size);

    default List<Aspsp> findAll() {
        return findAll(MAX_SIZE);
    }

    default List<Aspsp> findAll(int size) {
        return findAll(null, size);
    }

    List<Aspsp> findAll(String after, int size);

    default List<Aspsp> findLike(Aspsp aspsp) {
        return findLike(aspsp, DEFAULT_SIZE);
    }

    default List<Aspsp> findLike(Aspsp aspsp, int size) {
        return findLike(aspsp, null, size);
    }

    List<Aspsp> findLike(Aspsp aspsp, String after, int size);

    default List<Aspsp> findByIban(String iban) {
        return findByIban(iban, DEFAULT_SIZE);
    }

    default List<Aspsp> findByIban(String iban, int size) {
        return findByIban(iban, null, size);
    }

    List<Aspsp> findByIban(String iban, String after, int size);
}
