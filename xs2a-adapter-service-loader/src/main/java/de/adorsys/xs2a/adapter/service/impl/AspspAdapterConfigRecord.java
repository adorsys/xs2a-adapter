package de.adorsys.xs2a.adapter.service.impl;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({"aspspName", "bic", "url", "adapterId"})
public class AspspAdapterConfigRecord {
    private String aspspName;
    private String bic;
    private String url;
    private String adapterId;

    public String getAspspName() {
        return aspspName;
    }

    public void setAspspName(String aspspName) {
        this.aspspName = aspspName;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(String adapterId) {
        this.adapterId = adapterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AspspAdapterConfigRecord that = (AspspAdapterConfigRecord) o;
        return Objects.equals(aspspName, that.aspspName) &&
            Objects.equals(bic, that.bic) &&
            Objects.equals(url, that.url) &&
            Objects.equals(adapterId, that.adapterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aspspName, bic, url, adapterId);
    }
}
