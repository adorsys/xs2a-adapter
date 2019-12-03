package de.adorsys.xs2a.adapter.adorsys.service.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class AuthorisationServerMetaData {
    @JsonProperty("authorization_endpoint")
    private String authorisationEndpoint;
    @JsonProperty("token_endpoint")
    private String tokenEndpoint;
    @JsonProperty("response_types_supported")
    private List<String> responseTypesSupported;
    @JsonProperty("grant_types_supported")
    private List<String> grantTypesSupported;

    public String getAuthorisationEndpoint() {
        return authorisationEndpoint;
    }

    public void setAuthorisationEndpoint(String authorisationEndpoint) {
        this.authorisationEndpoint = authorisationEndpoint;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public List<String> getResponseTypesSupported() {
        return responseTypesSupported;
    }

    public void setResponseTypesSupported(List<String> responseTypesSupported) {
        this.responseTypesSupported = responseTypesSupported;
    }

    public List<String> getGrantTypesSupported() {
        return grantTypesSupported;
    }

    public void setGrantTypesSupported(List<String> grantTypesSupported) {
        this.grantTypesSupported = grantTypesSupported;
    }

    @Override
    public String toString() {
        return "AuthorisationServerMetaData{" +
                   "authorisationEndpoint='" + authorisationEndpoint + '\'' +
                   ", tokenEndpoint='" + tokenEndpoint + '\'' +
                   ", responseTypesSupported=" + responseTypesSupported +
                   ", grantTypesSupported=" + grantTypesSupported +
                   '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorisationServerMetaData that = (AuthorisationServerMetaData) o;
        return Objects.equals(authorisationEndpoint, that.authorisationEndpoint) &&
                   Objects.equals(tokenEndpoint, that.tokenEndpoint) &&
                   Objects.equals(responseTypesSupported, that.responseTypesSupported) &&
                   Objects.equals(grantTypesSupported, that.grantTypesSupported);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorisationEndpoint, tokenEndpoint, responseTypesSupported, grantTypesSupported);
    }
}
