package de.adorsys.xs2a.adapter.service.ing.internal.api.model;

public class ClearingSystemMemberIdentification {
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
