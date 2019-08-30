package de.adorsys.xs2a.adapter.service.psd2.model;

public class AuthenticationObject {
    private String authenticationType;

    private String authenticationVersion;

    private String authenticationMethodId;

    private String name;

    private String explanation;

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getAuthenticationVersion() {
        return authenticationVersion;
    }

    public void setAuthenticationVersion(String authenticationVersion) {
        this.authenticationVersion = authenticationVersion;
    }

    public String getAuthenticationMethodId() {
        return authenticationMethodId;
    }

    public void setAuthenticationMethodId(String authenticationMethodId) {
        this.authenticationMethodId = authenticationMethodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
