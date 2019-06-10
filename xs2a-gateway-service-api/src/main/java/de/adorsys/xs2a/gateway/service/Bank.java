package de.adorsys.xs2a.gateway.service;

import java.util.Set;

public class Bank {
    private String name;
    private Set<String> bankCodes;

    public Bank(String name, Set<String> bankCodes) {
        this.name = name;
        this.bankCodes = bankCodes;
    }

    public String getName() {
        return name;
    }

    public Set<String> getBankCodes() {
        return bankCodes;
    }
}
