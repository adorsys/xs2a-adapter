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

package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IngTransactions {
    private List<IngTransaction> booked;

    private List<IngTransaction> pending;

    private List<IngTransaction> info;

    @JsonProperty("_links")
    private IngLinksNext links;

    public List<IngTransaction> getBooked() {
        return booked;
    }

    public void setBooked(List<IngTransaction> booked) {
        this.booked = booked;
    }

    public List<IngTransaction> getPending() {
        return pending;
    }

    public void setPending(List<IngTransaction> pending) {
        this.pending = pending;
    }

    public List<IngTransaction> getInfo() {
        return info;
    }

    public void setInfo(List<IngTransaction> info) {
        this.info = info;
    }

    public IngLinksNext getLinks() {
        return links;
    }

    public void setLinks(IngLinksNext links) {
        this.links = links;
    }
}
