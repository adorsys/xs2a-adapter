/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.registry;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.adorsys.xs2a.adapter.api.model.AspspScaApproach;

import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"id", "aspspName", "bic", "url", "adapterId", "bankCode", "idpUrl", "aspspScaApproaches"})
public class AspspCsvRecord {
    private String id;
    private String aspspName;
    private String bic;
    private String url;
    private String bankCode;
    private String adapterId;
    private String idpUrl;
    private List<AspspScaApproach> aspspScaApproaches;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public List<AspspScaApproach> getAspspScaApproaches() {
        return aspspScaApproaches;
    }

    public void setAspspScaApproaches(List<AspspScaApproach> aspspScaApproaches) {
        this.aspspScaApproaches = aspspScaApproaches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AspspCsvRecord that = (AspspCsvRecord) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(aspspName, that.aspspName) &&
            Objects.equals(bic, that.bic) &&
            Objects.equals(url, that.url) &&
            Objects.equals(bankCode, that.bankCode) &&
            Objects.equals(adapterId, that.adapterId) &&
            Objects.equals(idpUrl, that.idpUrl) &&
            Objects.equals(aspspScaApproaches, that.aspspScaApproaches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aspspName, bic, url, bankCode, adapterId, idpUrl, aspspScaApproaches);
    }

    @Override
    public String toString() {
        return "AspspCsvRecord{" +
                   "id='" + id + '\'' +
                   ", aspspName='" + aspspName + '\'' +
                   ", bic='" + bic + '\'' +
                   ", url='" + url + '\'' +
                   ", bankCode='" + bankCode + '\'' +
                   ", adapterId='" + adapterId + '\'' +
                   ", idpUrl='" + idpUrl + '\'' +
                   ", aspspScaApproaches=" + aspspScaApproaches +
                   '}';
    }
}
