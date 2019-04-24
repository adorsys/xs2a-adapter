package de.adorsys.xs2a.gateway.service.account;

public class BankTransactionCode {
    private String code;

    public BankTransactionCode() {
    }

    public BankTransactionCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
