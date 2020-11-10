package de.adorsys.xs2a.adapter.unicredit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.HrefType;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UnicreditAccountReport {
    private List<UnicreditTransactionDetails> booked;
    private List<UnicreditTransactionDetails> pending;
    @JsonProperty("_links")
    private Map<String, HrefType> links;

    public List<UnicreditTransactionDetails> getBooked() {
        return booked;
    }

    public void setBooked(List<UnicreditTransactionDetails> booked) {
        this.booked = booked;
    }

    public List<UnicreditTransactionDetails> getPending() {
        return pending;
    }

    public void setPending(List<UnicreditTransactionDetails> pending) {
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
        UnicreditAccountReport that = (UnicreditAccountReport) o;
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
