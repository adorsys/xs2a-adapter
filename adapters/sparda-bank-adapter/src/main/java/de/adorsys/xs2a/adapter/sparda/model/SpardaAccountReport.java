package de.adorsys.xs2a.adapter.sparda.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.HrefType;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpardaAccountReport {
    private List<SpardaTransactionDetails> booked;
    private List<SpardaTransactionDetails> pending;
    @JsonProperty("_links")
    private Map<String, HrefType> links;

    public List<SpardaTransactionDetails> getBooked() {
        return booked;
    }

    public void setBooked(List<SpardaTransactionDetails> booked) {
        this.booked = booked;
    }

    public List<SpardaTransactionDetails> getPending() {
        return pending;
    }

    public void setPending(List<SpardaTransactionDetails> pending) {
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
        SpardaAccountReport that = (SpardaAccountReport) o;
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
