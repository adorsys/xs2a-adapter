package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public class PeriodicPaymentInitiationMultipartBodyTO {
  private Object xml_sct;

  private PeriodicPaymentInitiationXmlPart2StandingorderTypeJsonTO json_standingorderType;

  public Object getXml_sct() {
    return xml_sct;
  }

  public void setXml_sct(Object xml_sct) {
    this.xml_sct = xml_sct;
  }

  public PeriodicPaymentInitiationXmlPart2StandingorderTypeJsonTO getJson_standingorderType() {
    return json_standingorderType;
  }

  public void setJson_standingorderType(
      PeriodicPaymentInitiationXmlPart2StandingorderTypeJsonTO json_standingorderType) {
    this.json_standingorderType = json_standingorderType;
  }
}
