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
