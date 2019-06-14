package de.adorsys.xs2a.adapter.service;

import java.util.Objects;
import java.util.Set;

public class Bank {
    private String name;
    private Set<String> bankCodes;

    public Bank(String name, Set<String> bankCodes) {
        this.name = name;
        this.bankCodes = bankCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(name, bank.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public Set<String> getBankCodes() {
        return bankCodes;
    }
}
