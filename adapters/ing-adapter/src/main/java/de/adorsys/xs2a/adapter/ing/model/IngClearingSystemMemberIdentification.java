package de.adorsys.xs2a.adapter.ing.model;

public class IngClearingSystemMemberIdentification {
    private String clearingSystemIdentificationCode;

    private String memberIdentification;

    public String getClearingSystemIdentificationCode() {
        return clearingSystemIdentificationCode;
    }

    public void setClearingSystemIdentificationCode(String clearingSystemIdentificationCode) {
        this.clearingSystemIdentificationCode = clearingSystemIdentificationCode;
    }

    public String getMemberIdentification() {
        return memberIdentification;
    }

    public void setMemberIdentification(String memberIdentification) {
        this.memberIdentification = memberIdentification;
    }
}
