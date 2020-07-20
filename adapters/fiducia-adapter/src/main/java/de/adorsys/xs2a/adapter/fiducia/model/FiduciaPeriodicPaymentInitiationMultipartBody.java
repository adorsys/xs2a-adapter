package de.adorsys.xs2a.adapter.fiducia.model;

import java.util.Objects;

public class FiduciaPeriodicPaymentInitiationMultipartBody {
    private Object xml_sct;

    private FiduciaPeriodicPaymentInitiationXmlPart2StandingorderTypeJson json_standingorderType;

    public Object getXml_sct() {
        return xml_sct;
    }

    public void setXml_sct(Object xml_sct) {
        this.xml_sct = xml_sct;
    }

    public FiduciaPeriodicPaymentInitiationXmlPart2StandingorderTypeJson getJson_standingorderType() {
        return json_standingorderType;
    }

    public void setJson_standingorderType(
        FiduciaPeriodicPaymentInitiationXmlPart2StandingorderTypeJson json_standingorderType) {
        this.json_standingorderType = json_standingorderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaPeriodicPaymentInitiationMultipartBody that = (FiduciaPeriodicPaymentInitiationMultipartBody) o;
        return Objects.equals(xml_sct, that.xml_sct) &&
            Objects.equals(json_standingorderType, that.json_standingorderType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xml_sct,
            json_standingorderType);
    }
}
