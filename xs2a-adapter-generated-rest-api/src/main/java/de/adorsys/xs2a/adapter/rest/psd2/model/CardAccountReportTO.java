package de.adorsys.xs2a.adapter.rest.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public class CardAccountReportTO {
    private List<CardTransactionTO> booked;

    private List<CardTransactionTO> pending;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    public List<CardTransactionTO> getBooked() {
        return booked;
    }

    public void setBooked(List<CardTransactionTO> booked) {
        this.booked = booked;
    }

    public List<CardTransactionTO> getPending() {
        return pending;
    }

    public void setPending(List<CardTransactionTO> pending) {
        this.pending = pending;
    }

    public Map<String, HrefTypeTO> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefTypeTO> links) {
        this.links = links;
    }
}
