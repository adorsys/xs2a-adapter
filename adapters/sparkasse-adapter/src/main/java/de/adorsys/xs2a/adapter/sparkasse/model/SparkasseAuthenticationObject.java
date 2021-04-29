package de.adorsys.xs2a.adapter.sparkasse.model;

import java.util.Objects;

public class SparkasseAuthenticationObject {
    private SparkasseAuthenticationType authenticationType;

    private String authenticationVersion;

    private String authenticationMethodId;

    private String name;

    private String explanation;

    public SparkasseAuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(SparkasseAuthenticationType authenticationType) {
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
        SparkasseAuthenticationObject that = (SparkasseAuthenticationObject) o;
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
