package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class ClearingSystemMemberIdentificationTO {
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
