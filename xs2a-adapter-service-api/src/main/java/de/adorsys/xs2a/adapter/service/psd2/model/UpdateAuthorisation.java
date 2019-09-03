package de.adorsys.xs2a.adapter.service.psd2.model;

public class UpdateAuthorisation {
    private PsuData psuData;
    private String authenticationMethodId;
    private String scaAuthenticationData;

    public PsuData getPsuData() {
        return psuData;
    }

    public void setPsuData(PsuData psuData) {
        this.psuData = psuData;
    }

    public String getAuthenticationMethodId() {
        return authenticationMethodId;
    }

    public void setAuthenticationMethodId(String authenticationMethodId) {
        this.authenticationMethodId = authenticationMethodId;
    }

    public String getScaAuthenticationData() {
        return scaAuthenticationData;
    }

    public void setScaAuthenticationData(String scaAuthenticationData) {
        this.scaAuthenticationData = scaAuthenticationData;
    }
}
