package de.adorsys.xs2a.adapter.model;

import java.util.Set;

public class BankTO {
    private String name;
    private Set<String> bankCodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getBankCodes() {
        return bankCodes;
    }

    public void setBankCodes(Set<String> bankCodes) {
        this.bankCodes = bankCodes;
    }
}
