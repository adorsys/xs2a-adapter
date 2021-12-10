package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class CardAccountReport {
    private List<CardTransaction> booked;

    private List<CardTransaction> pending;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

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
        CardAccountReport that = (CardAccountReport) o;
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
