package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class CardAccountReport {
    private List<CardTransaction> booked;

    private List<CardTransaction> pending;

    @JsonProperty("_links")
    private Map<String, Link> links;

    public List<CardTransaction> getBooked() {
        return booked;
    }

    public void setBooked(List<CardTransaction> booked) {
        this.booked = booked;
    }

    public List<CardTransaction> getPending() {
        return pending;
    }

    public void setPending(List<CardTransaction> pending) {
        this.pending = pending;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
