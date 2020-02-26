package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public class AccountReportTO {
    private List<TransactionDetailsTO> booked;

    private List<TransactionDetailsTO> pending;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    public List<TransactionDetailsTO> getBooked() {
        return booked;
    }

    public void setBooked(List<TransactionDetailsTO> booked) {
        this.booked = booked;
    }

    public List<TransactionDetailsTO> getPending() {
        return pending;
    }

    public void setPending(List<TransactionDetailsTO> pending) {
        this.pending = pending;
    }

    public Map<String, HrefTypeTO> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefTypeTO> links) {
        this.links = links;
    }
}
