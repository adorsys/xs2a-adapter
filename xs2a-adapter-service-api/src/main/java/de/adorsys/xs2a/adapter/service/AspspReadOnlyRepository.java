package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.Aspsp;

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
