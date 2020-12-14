package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AccountReport {
    private List<Transactions> booked;

    private List<Transactions> pending;

    private List<Transactions> information;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

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

    public List<Transactions> getInformation() {
        return information;
    }

    public void setInformation(List<Transactions> information) {
        this.information = information;
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
        AccountReport that = (AccountReport) o;
        return Objects.equals(booked, that.booked) &&
            Objects.equals(pending, that.pending) &&
            Objects.equals(information, that.information) &&
            Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booked,
            pending,
            information,
            links);
    }
}
