package de.adorsys.xs2a.adapter.adapter.oauth2.api;

public interface Oauth2Api {

    String getAuthorisationUri(String scaOAuthUrl);

    String getTokenUri(String scaOAuthUrl);
}
