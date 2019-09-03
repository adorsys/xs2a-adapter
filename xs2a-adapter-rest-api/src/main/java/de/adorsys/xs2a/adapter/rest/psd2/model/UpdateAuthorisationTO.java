package de.adorsys.xs2a.adapter.rest.psd2.model;

public class UpdateAuthorisationTO {
    private PsuDataTO psuData;
    private String authenticationMethodId;
    private String scaAuthenticationData;

    public PsuDataTO getPsuData() {
        return psuData;
    }

    public void setPsuData(PsuDataTO psuData) {
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
