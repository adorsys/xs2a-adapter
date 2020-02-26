package de.adorsys.xs2a.adapter.model;

import java.util.List;
import java.util.Objects;

public class AspspTO {
    private String id;
    private String name;
    private String bic;
    private String bankCode;
    private String url;
    private String adapterId;
    private String idpUrl;
    private List<AspspScaApproachTO> scaApproaches;
    private String paginationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public String getIdpUrl() {
        return idpUrl;
    }

    public void setIdpUrl(String idpUrl) {
        this.idpUrl = idpUrl;
    }

    public List<AspspScaApproachTO> getScaApproaches() {
        return scaApproaches;
    }

    public void setScaApproaches(List<AspspScaApproachTO> scaApproaches) {
        this.scaApproaches = scaApproaches;
    }

    public String getPaginationId() {
        return paginationId;
    }

    public void setPaginationId(String paginationId) {
        this.paginationId = paginationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AspspTO aspspTO = (AspspTO) o;
        return Objects.equals(id, aspspTO.id) &&
                   Objects.equals(name, aspspTO.name) &&
                   Objects.equals(bic, aspspTO.bic) &&
                   Objects.equals(bankCode, aspspTO.bankCode) &&
                   Objects.equals(url, aspspTO.url) &&
                   Objects.equals(adapterId, aspspTO.adapterId) &&
                   Objects.equals(idpUrl, aspspTO.idpUrl) &&
                   Objects.equals(scaApproaches, aspspTO.scaApproaches) &&
                   Objects.equals(paginationId, aspspTO.paginationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bic, bankCode, url, adapterId, idpUrl, scaApproaches, paginationId);
    }
}
