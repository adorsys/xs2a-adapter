package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public class AuthenticationObjectTO {
  private AuthenticationTypeTO authenticationType;

  private String authenticationVersion;

  private String authenticationMethodId;

  private String name;

  private String explanation;

  public AuthenticationTypeTO getAuthenticationType() {
    return authenticationType;
  }

  public void setAuthenticationType(AuthenticationTypeTO authenticationType) {
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
