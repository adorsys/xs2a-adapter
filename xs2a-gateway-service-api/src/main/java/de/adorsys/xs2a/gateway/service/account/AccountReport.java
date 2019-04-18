package de.adorsys.xs2a.gateway.service.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.gateway.service.Links;

import java.util.List;

public class AccountReport {

    private List<Transactions> booked;
    private List<Transactions> pending;
    private byte[] transactionsRaw;

    @JsonProperty("_links")
    private Links links;

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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
