package de.adorsys.xs2a.adapter.ing.internal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Transactions {
    private List<Transaction> booked;

    private List<Transaction> pending;

    private List<Transaction> info;

    @JsonProperty("_links")
    private LinksNext links;

    public List<Transaction> getBooked() {
        return booked;
    }

    public void setBooked(List<Transaction> booked) {
        this.booked = booked;
    }

    public List<Transaction> getPending() {
        return pending;
    }

    public void setPending(List<Transaction> pending) {
        this.pending = pending;
    }

    public List<Transaction> getInfo() {
        return info;
    }

    public void setInfo(List<Transaction> info) {
        this.info = info;
    }

    public LinksNext getLinks() {
        return links;
    }

    public void setLinks(LinksNext links) {
        this.links = links;
    }
}
