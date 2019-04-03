package de.adorsys.xs2a.gateway.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// verified that structure compatibility with
// Error401_NG_PIS
// Error400_NG_PIS
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private List<TppMessage> tppMessages;
    @JsonProperty("_links")
    private Links links;

    public List<TppMessage> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessage> tppMessages) {
        this.tppMessages = tppMessages;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
