package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// verified that structure is compatible with
// Error400_NG_PIS
// Error401_NG_PIS
// Error403_NG_PIS
// Error404_NG_PIS
// Error405_NG_PIS
// Error400_NG_AIS
// Error401_NG_AIS
// Error403_NG_AIS
// Error404_NG_AIS
// Error405_NG_AIS
// Error406_NG_AIS
// Error409_NG_AIS
// Error429_NG_AIS
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
