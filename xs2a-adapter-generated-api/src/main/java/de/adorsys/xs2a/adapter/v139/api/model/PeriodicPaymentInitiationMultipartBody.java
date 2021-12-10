package de.adorsys.xs2a.adapter.v139.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class PeriodicPaymentInitiationMultipartBody {
    private Object xml_sct;

    private PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json_standingorderType;

    public Object getXml_sct() {
        return xml_sct;
    }

    public void setXml_sct(Object xml_sct) {
        this.xml_sct = xml_sct;
    }

    public PeriodicPaymentInitiationXmlPart2StandingorderTypeJson getJson_standingorderType() {
        return json_standingorderType;
    }

    public void setJson_standingorderType(
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json_standingorderType) {
        this.json_standingorderType = json_standingorderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodicPaymentInitiationMultipartBody that = (PeriodicPaymentInitiationMultipartBody) o;
        return Objects.equals(xml_sct, that.xml_sct) &&
            Objects.equals(json_standingorderType, that.json_standingorderType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xml_sct,
            json_standingorderType);
    }
}
