package de.adorsys.xs2a.adapter.impl.adapter.oauth2.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

// According to RFC 8414 (https://tools.ietf.org/html/rfc8414#section-2)
public class AuthorisationServerMetaData {
    /*
     * The authorization server's issuer identifier, which is
     * a URL that uses the "https" scheme and has no query or fragment
     * components.
     */
    @JsonProperty("issuer")
    private String issuer;

    /*
     * URL of the authorization server's authorization endpoint
     */
    @JsonProperty("authorization_endpoint")
    private String authorisationEndpoint;

    /*
     * URL of the authorization server's token endpoint
     */
    @JsonProperty("token_endpoint")
    private String tokenEndpoint;

    /*
     * URL of the authorization server's JWK Set [JWK] document.
     */
    @JsonProperty("jwks_uri")
    private String jwksUri;

    /*
     * URL of the authorization server's OAuth 2.0 Dynamic Client Registration endpoint
     */
    @JsonProperty("registration_endpoint")
    private String registrationEndpoint;

    /*
     * JSON array containing a list of the OAuth 2.0
     * [RFC6749] "scope" values that this authorization server supports.
     */
    @JsonProperty("scopes_supported")
    private List<String> scopesSupported;

    /*
     * JSON array containing a list of the OAuth 2.0
     * "response_type" values that this authorization server supports.
     */
    @JsonProperty("response_types_supported")
    private List<String> responseTypesSupported;

    /*
     * JSON array containing a list of the OAuth 2.0
     * "response_mode" values that this authorization server supports
     */
    @JsonProperty("response_modes_supported")
    private List<String> responseModesSupported;

    /*
     * JSON array containing a list of the OAuth 2.0 grant
     * type values that this authorization server supports
     */
    @JsonProperty("grant_types_supported")
    private List<String> grantTypesSupported;

    /*
     * JSON array containing a list of client authentication
     * methods supported by this token endpoint.
     */
    @JsonProperty("token_endpoint_auth_methods_supported")
    private List<String> tokenEndpointAuthMethodsSupported;

    /*
     * JSON array containing a list of the JWS signing
     * algorithms ("alg" values) supported by the token endpoint for the
     * signature on the JWT used to authenticate the client at the
     * token endpoint for the "private_key_jwt" and "client_secret_jwt"
     * authentication methods.
     */
    @JsonProperty("token_endpoint_auth_signing_alg_values_supported")
    private List<String> tokenEndpointAuthSigningAlgValuesSupported;

    /*
     * URL of a page containing human-readable information
     * that developers might want or need to know when using the
     * authorization server.
     */
    @JsonProperty("service_documentation")
    private String serviceDocumentation;

    /*
     * Languages and scripts supported for the user interface,
     * represented as a JSON array of language tag values from BCP 47
     */
    @JsonProperty("ui_locales_supported")
    private List<String> uiLocalesSupported;

    /*
     * URL that the authorization server provides to the
     * person registering the client to read about the authorization
     * server's requirements on how the client can use the data provided
     * by the authorization server
     */
    @JsonProperty("op_policy_uri")
    private String opPolicyUri;

    /*
     * URL that the authorization server provides to the person
     * registering the client to read about the authorization
     * server's terms of service
     */
    @JsonProperty("op_tos_uri")
    private String opTosUri;

    /*
     * URL of the authorization server's OAuth 2.0 revocation endpoint
     */
    @JsonProperty("revocation_endpoint")
    private String revocationEndpoint;

    /*
     * JSON array containing a list of client authentication
     * methods supported by this revocation endpoint
     */
    @JsonProperty("revocation_endpoint_auth_methods_supported")
    private List<String> revocationEndpointAuthMethodsSupported;

    /*
     * JSON array containing a list of the JWS signing
     * algorithms ("alg" values) supported by the revocation endpoint for
     * the signature on the JWT used to authenticate the client at
     * the revocation endpoint for the "private_key_jwt" and
     * "client_secret_jwt" authentication methods.
     */
    @JsonProperty("revocation_endpoint_auth_signing_alg_values_supported")
    private List<String> revocationEndpointAuthSigningAlgValuesSupported;

    /*
     * URL of the authorization server's OAuth 2.0 introspection endpoint
     */
    @JsonProperty("introspection_endpoint")
    private String introspectionEndpoint;

    /*
     * JSON array containing a list of client authentication
     * methods supported by this introspection endpoint
     */
    @JsonProperty("introspection_endpoint_auth_methods_supported")
    private List<String> introspectionEndpointAuthMethodsSupported;

    /*
     * JSON array containing a list of the JWS signing
     * algorithms ("alg" values) supported by the introspection endpoint
     * for the signature on the JWT used to authenticate the client
     * at the introspection endpoint for the "private_key_jwt" and
     * "client_secret_jwt" authentication methods.
     */
    @JsonProperty("introspection_endpoint_auth_signing_alg_values_supported")
    private List<String> introspectionEndpointAuthSigningAlgValuesSupported;

    /*
     * JSON array containing a list of Proof Key for Code
     * Exchange (PKCE) code challenge methods supported by this
     * authorization server.
     */
    @JsonProperty("code_challenge_methods_supported")
    private List<String> codeChallengeMethodsSupported;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

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

    public String getJwksUri() {
        return jwksUri;
    }

    public void setJwksUri(String jwksUri) {
        this.jwksUri = jwksUri;
    }

    public String getRegistrationEndpoint() {
        return registrationEndpoint;
    }

    public void setRegistrationEndpoint(String registrationEndpoint) {
        this.registrationEndpoint = registrationEndpoint;
    }

    public List<String> getScopesSupported() {
        return scopesSupported;
    }

    public void setScopesSupported(List<String> scopesSupported) {
        this.scopesSupported = scopesSupported;
    }

    public List<String> getResponseTypesSupported() {
        return responseTypesSupported;
    }

    public void setResponseTypesSupported(List<String> responseTypesSupported) {
        this.responseTypesSupported = responseTypesSupported;
    }

    public List<String> getResponseModesSupported() {
        return responseModesSupported;
    }

    public void setResponseModesSupported(List<String> responseModesSupported) {
        this.responseModesSupported = responseModesSupported;
    }

    public List<String> getGrantTypesSupported() {
        return grantTypesSupported;
    }

    public void setGrantTypesSupported(List<String> grantTypesSupported) {
        this.grantTypesSupported = grantTypesSupported;
    }

    public List<String> getTokenEndpointAuthMethodsSupported() {
        return tokenEndpointAuthMethodsSupported;
    }

    public void setTokenEndpointAuthMethodsSupported(List<String> tokenEndpointAuthMethodsSupported) {
        this.tokenEndpointAuthMethodsSupported = tokenEndpointAuthMethodsSupported;
    }

    public List<String> getTokenEndpointAuthSigningAlgValuesSupported() {
        return tokenEndpointAuthSigningAlgValuesSupported;
    }

    public void setTokenEndpointAuthSigningAlgValuesSupported(List<String> tokenEndpointAuthSigningAlgValuesSupported) {
        this.tokenEndpointAuthSigningAlgValuesSupported = tokenEndpointAuthSigningAlgValuesSupported;
    }

    public String getServiceDocumentation() {
        return serviceDocumentation;
    }

    public void setServiceDocumentation(String serviceDocumentation) {
        this.serviceDocumentation = serviceDocumentation;
    }

    public List<String> getUiLocalesSupported() {
        return uiLocalesSupported;
    }

    public void setUiLocalesSupported(List<String> uiLocalesSupported) {
        this.uiLocalesSupported = uiLocalesSupported;
    }

    public String getOpPolicyUri() {
        return opPolicyUri;
    }

    public void setOpPolicyUri(String opPolicyUri) {
        this.opPolicyUri = opPolicyUri;
    }

    public String getOpTosUri() {
        return opTosUri;
    }

    public void setOpTosUri(String opTosUri) {
        this.opTosUri = opTosUri;
    }

    public String getRevocationEndpoint() {
        return revocationEndpoint;
    }

    public void setRevocationEndpoint(String revocationEndpoint) {
        this.revocationEndpoint = revocationEndpoint;
    }

    public List<String> getRevocationEndpointAuthMethodsSupported() {
        return revocationEndpointAuthMethodsSupported;
    }

    public void setRevocationEndpointAuthMethodsSupported(List<String> revocationEndpointAuthMethodsSupported) {
        this.revocationEndpointAuthMethodsSupported = revocationEndpointAuthMethodsSupported;
    }

    public List<String> getRevocationEndpointAuthSigningAlgValuesSupported() {
        return revocationEndpointAuthSigningAlgValuesSupported;
    }

    public void setRevocationEndpointAuthSigningAlgValuesSupported(List<String> revocationEndpointAuthSigningAlgValuesSupported) {
        this.revocationEndpointAuthSigningAlgValuesSupported = revocationEndpointAuthSigningAlgValuesSupported;
    }

    public String getIntrospectionEndpoint() {
        return introspectionEndpoint;
    }

    public void setIntrospectionEndpoint(String introspectionEndpoint) {
        this.introspectionEndpoint = introspectionEndpoint;
    }

    public List<String> getIntrospectionEndpointAuthMethodsSupported() {
        return introspectionEndpointAuthMethodsSupported;
    }

    public void setIntrospectionEndpointAuthMethodsSupported(List<String> introspectionEndpointAuthMethodsSupported) {
        this.introspectionEndpointAuthMethodsSupported = introspectionEndpointAuthMethodsSupported;
    }

    public List<String> getIntrospectionEndpointAuthSigningAlgValuesSupported() {
        return introspectionEndpointAuthSigningAlgValuesSupported;
    }

    public void setIntrospectionEndpointAuthSigningAlgValuesSupported(List<String> introspectionEndpointAuthSigningAlgValuesSupported) {
        this.introspectionEndpointAuthSigningAlgValuesSupported = introspectionEndpointAuthSigningAlgValuesSupported;
    }

    public List<String> getCodeChallengeMethodsSupported() {
        return codeChallengeMethodsSupported;
    }

    public void setCodeChallengeMethodsSupported(List<String> codeChallengeMethodsSupported) {
        this.codeChallengeMethodsSupported = codeChallengeMethodsSupported;
    }

    @Override
    public String toString() {
        return "AuthorisationServerMetaData{" +
                   "issuer='" + issuer + '\'' +
                   ", authorisationEndpoint='" + authorisationEndpoint + '\'' +
                   ", tokenEndpoint='" + tokenEndpoint + '\'' +
                   ", jwksUri='" + jwksUri + '\'' +
                   ", registrationEndpoint='" + registrationEndpoint + '\'' +
                   ", scopesSupported=" + scopesSupported +
                   ", responseTypesSupported=" + responseTypesSupported +
                   ", responseModesSupported=" + responseModesSupported +
                   ", grantTypesSupported=" + grantTypesSupported +
                   ", tokenEndpointAuthMethodsSupported=" + tokenEndpointAuthMethodsSupported +
                   ", tokenEndpointAuthSigningAlgValuesSupported=" + tokenEndpointAuthSigningAlgValuesSupported +
                   ", serviceDocumentation='" + serviceDocumentation + '\'' +
                   ", uiLocalesSupported=" + uiLocalesSupported +
                   ", opPolicyUri='" + opPolicyUri + '\'' +
                   ", opTosUri='" + opTosUri + '\'' +
                   ", revocationEndpoint='" + revocationEndpoint + '\'' +
                   ", revocationEndpointAuthMethodsSupported=" + revocationEndpointAuthMethodsSupported +
                   ", revocationEndpointAuthSigningAlgValuesSupported=" + revocationEndpointAuthSigningAlgValuesSupported +
                   ", introspectionEndpoint='" + introspectionEndpoint + '\'' +
                   ", introspectionEndpointAuthMethodsSupported=" + introspectionEndpointAuthMethodsSupported +
                   ", introspectionEndpointAuthSigningAlgValuesSupported=" + introspectionEndpointAuthSigningAlgValuesSupported +
                   ", codeChallengeMethodsSupported=" + codeChallengeMethodsSupported +
                   '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorisationServerMetaData that = (AuthorisationServerMetaData) o;
        return Objects.equals(issuer, that.issuer) &&
                   Objects.equals(authorisationEndpoint, that.authorisationEndpoint) &&
                   Objects.equals(tokenEndpoint, that.tokenEndpoint) &&
                   Objects.equals(jwksUri, that.jwksUri) &&
                   Objects.equals(registrationEndpoint, that.registrationEndpoint) &&
                   Objects.equals(scopesSupported, that.scopesSupported) &&
                   Objects.equals(responseTypesSupported, that.responseTypesSupported) &&
                   Objects.equals(responseModesSupported, that.responseModesSupported) &&
                   Objects.equals(grantTypesSupported, that.grantTypesSupported) &&
                   Objects.equals(tokenEndpointAuthMethodsSupported, that.tokenEndpointAuthMethodsSupported) &&
                   Objects.equals(tokenEndpointAuthSigningAlgValuesSupported, that.tokenEndpointAuthSigningAlgValuesSupported) &&
                   Objects.equals(serviceDocumentation, that.serviceDocumentation) &&
                   Objects.equals(uiLocalesSupported, that.uiLocalesSupported) &&
                   Objects.equals(opPolicyUri, that.opPolicyUri) &&
                   Objects.equals(opTosUri, that.opTosUri) &&
                   Objects.equals(revocationEndpoint, that.revocationEndpoint) &&
                   Objects.equals(revocationEndpointAuthMethodsSupported, that.revocationEndpointAuthMethodsSupported) &&
                   Objects.equals(revocationEndpointAuthSigningAlgValuesSupported, that.revocationEndpointAuthSigningAlgValuesSupported) &&
                   Objects.equals(introspectionEndpoint, that.introspectionEndpoint) &&
                   Objects.equals(introspectionEndpointAuthMethodsSupported, that.introspectionEndpointAuthMethodsSupported) &&
                   Objects.equals(introspectionEndpointAuthSigningAlgValuesSupported, that.introspectionEndpointAuthSigningAlgValuesSupported) &&
                   Objects.equals(codeChallengeMethodsSupported, that.codeChallengeMethodsSupported);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            issuer, authorisationEndpoint, tokenEndpoint, jwksUri,
            registrationEndpoint, scopesSupported, responseTypesSupported,
            responseModesSupported, grantTypesSupported, tokenEndpointAuthMethodsSupported,
            tokenEndpointAuthSigningAlgValuesSupported, serviceDocumentation,
            uiLocalesSupported, opPolicyUri, opTosUri, revocationEndpoint,
            revocationEndpointAuthMethodsSupported,
            revocationEndpointAuthSigningAlgValuesSupported, introspectionEndpoint,
            introspectionEndpointAuthMethodsSupported,
            introspectionEndpointAuthSigningAlgValuesSupported,
            codeChallengeMethodsSupported
        );
    }
}
