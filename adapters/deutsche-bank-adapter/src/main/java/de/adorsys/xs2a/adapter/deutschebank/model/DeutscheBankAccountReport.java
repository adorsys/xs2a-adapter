package de.adorsys.xs2a.adapter.deutschebank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.HrefType;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DeutscheBankAccountReport {
    private List<DeutscheBankTransactionDetails> booked;
    private List<DeutscheBankTransactionDetails> pending;
    @JsonProperty("_links")
    private Map<String, HrefType> links;

    public List<DeutscheBankTransactionDetails> getBooked() {
        return booked;
    }

    public void setBooked(List<DeutscheBankTransactionDetails> booked) {
        this.booked = booked;
    }

    public List<DeutscheBankTransactionDetails> getPending() {
        return pending;
    }

    public void setPending(List<DeutscheBankTransactionDetails> pending) {
        this.pending = pending;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeutscheBankAccountReport that = (DeutscheBankAccountReport) o;
        return Objects.equals(booked, that.booked) &&
            Objects.equals(pending, that.pending) &&
            Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booked,
            pending,
            links);
    }
}
