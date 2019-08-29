package de.adorsys.xs2a.adapter.service.psd2.model;

import java.util.List;
import java.util.Map;

public class AccountReport {
    private List<TransactionDetails> booked;

    private List<TransactionDetails> pending;

    private Map<String, HrefType> links;

    public List<TransactionDetails> getBooked() {
        return booked;
    }

    public void setBooked(List<TransactionDetails> booked) {
        this.booked = booked;
    }

    public List<TransactionDetails> getPending() {
        return pending;
    }

    public void setPending(List<TransactionDetails> pending) {
        this.pending = pending;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }
}
