package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AuthenticationObject {
    private AuthenticationType authenticationType;

    private String authenticationVersion;

    private String authenticationMethodId;

    private String name;

    private String explanation;

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationObject that = (AuthenticationObject) o;
        return Objects.equals(authenticationType, that.authenticationType) &&
            Objects.equals(authenticationVersion, that.authenticationVersion) &&
            Objects.equals(authenticationMethodId, that.authenticationMethodId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(explanation, that.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authenticationType,
            authenticationVersion,
            authenticationMethodId,
            name,
            explanation);
    }
}
