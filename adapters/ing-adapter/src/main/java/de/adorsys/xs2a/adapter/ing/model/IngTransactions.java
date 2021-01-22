package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IngTransactions {
    private List<IngTransaction> booked;

    private List<IngTransaction> pending;

    private List<IngTransaction> info;

    @JsonProperty("_links")
    private IngLinksNext links;

    public List<IngTransaction> getBooked() {
        return booked;
    }

    public void setBooked(List<IngTransaction> booked) {
        this.booked = booked;
    }

    public List<IngTransaction> getPending() {
        return pending;
    }

    public void setPending(List<IngTransaction> pending) {
        this.pending = pending;
    }

    public List<IngTransaction> getInfo() {
        return info;
    }

    public void setInfo(List<IngTransaction> info) {
        this.info = info;
    }

    public IngLinksNext getLinks() {
        return links;
    }

    public void setLinks(IngLinksNext links) {
        this.links = links;
    }
}
