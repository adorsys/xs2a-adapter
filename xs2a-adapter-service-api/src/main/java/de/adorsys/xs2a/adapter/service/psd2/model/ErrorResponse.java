package de.adorsys.xs2a.adapter.service.psd2.model;

import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private List<TppMessage> tppMessages;

    private Map<String, HrefType> links;

    public List<TppMessage> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessage> tppMessages) {
        this.tppMessages = tppMessages;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }
}
