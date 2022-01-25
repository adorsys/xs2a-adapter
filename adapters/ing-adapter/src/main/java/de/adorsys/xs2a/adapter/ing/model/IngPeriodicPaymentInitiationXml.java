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

import java.time.LocalDate;

public class IngPeriodicPaymentInitiationXml {
    private String xml_sct;

    private LocalDate startDate;

    private LocalDate endDate;

    private IngFrequencyCode frequency;

    private IngDayOfExecution dayOfExecution;

    public String getXml_sct() {
        return xml_sct;
    }

    public void setXml_sct(String xml_sct) {
        this.xml_sct = xml_sct;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public IngFrequencyCode getFrequency() {
        return frequency;
    }

    public void setFrequency(IngFrequencyCode frequency) {
        this.frequency = frequency;
    }

    public IngDayOfExecution getDayOfExecution() {
        return dayOfExecution;
    }

    public void setDayOfExecution(IngDayOfExecution dayOfExecution) {
        this.dayOfExecution = dayOfExecution;
    }
}
