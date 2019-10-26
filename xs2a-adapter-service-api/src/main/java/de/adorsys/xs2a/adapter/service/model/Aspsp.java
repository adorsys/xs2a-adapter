package de.adorsys.xs2a.adapter.service.model;

import java.util.List;
import java.util.Objects;

// all setters should treat an empty value as null
public class Aspsp {
    private String id;
    private String name;
    private String bic;
    private String bankCode;
    private String url;
    private String adapterId;
    private String idpUrl;
    private List<AspspScaApproach> scaApproaches;
    private String paginationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = emptyAsNull(id);
    }

    private String emptyAsNull(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        return s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = emptyAsNull(name);
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = emptyAsNull(bic);
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = emptyAsNull(bankCode);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = emptyAsNull(url);
    }

    public String getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(String adapterId) {
        this.adapterId = emptyAsNull(adapterId);
    }

    public String getIdpUrl() {
        return idpUrl;
    }

    public void setIdpUrl(String idpUrl) {
        this.idpUrl = emptyAsNull(idpUrl);
    }

    public List<AspspScaApproach> getScaApproaches() {
        return scaApproaches;
    }

    public void setScaApproaches(List<AspspScaApproach> scaApproaches) {
        this.scaApproaches = emptyAsNull(scaApproaches);
    }

    private List<AspspScaApproach> emptyAsNull(List<AspspScaApproach> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public String getPaginationId() {
        return paginationId;
    }

    public void setPaginationId(String paginationId) {
        this.paginationId = paginationId;
    }

    @Override
    public String toString() {
        return "Aspsp{" +
                   "id='" + id + '\'' +
                   ", name='" + name + '\'' +
                   ", bic='" + bic + '\'' +
                   ", bankCode='" + bankCode + '\'' +
                   ", url='" + url + '\'' +
                   ", adapterId='" + adapterId + '\'' +
                   ", idpUrl='" + idpUrl + '\'' +
                   ", scaApproaches=" + scaApproaches +
                   ", paginationId='" + paginationId + '\'' +
                   '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aspsp aspsp = (Aspsp) o;
        return Objects.equals(id, aspsp.id) &&
                   Objects.equals(name, aspsp.name) &&
                   Objects.equals(bic, aspsp.bic) &&
                   Objects.equals(bankCode, aspsp.bankCode) &&
                   Objects.equals(url, aspsp.url) &&
                   Objects.equals(adapterId, aspsp.adapterId) &&
                   Objects.equals(idpUrl, aspsp.idpUrl) &&
                   Objects.equals(scaApproaches, aspsp.scaApproaches) &&
                   Objects.equals(paginationId, aspsp.paginationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bic, bankCode, url, adapterId, idpUrl, scaApproaches, paginationId);
    }
}
