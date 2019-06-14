package de.adorsys.xs2a.adapter.service.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.List;
import java.util.Map;

public class AccountReport {

    private List<Transactions> booked;
    private List<Transactions> pending;
    private byte[] transactionsRaw;

    @JsonProperty("_links")
    private Map<String, Link> links;

    public List<Transactions> getBooked() {
        return booked;
    }

    public void setBooked(List<Transactions> booked) {
        this.booked = booked;
    }

    public List<Transactions> getPending() {
        return pending;
    }

    public void setPending(List<Transactions> pending) {
        this.pending = pending;
    }

    public byte[] getTransactionsRaw() {
        return transactionsRaw;
    }

    public void setTransactionsRaw(byte[] transactionsRaw) {
        this.transactionsRaw = transactionsRaw;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}
