package de.adorsys.xs2a.adapter.api.oauth;

public interface Oauth2Api {

    String getAuthorisationUri(String scaOAuthUrl);

    String getTokenUri(String scaOAuthUrl);
}
