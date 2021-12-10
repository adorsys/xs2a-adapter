package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class ErrorResponse {
  private List<TppMessage> tppMessages;

  @JsonProperty("_links")
  private Map<String, HrefType> links;

  public List<TppMessage> getTppMessages() {
    return tppMessages;
  }

  public void setTppMessages(List<TppMessage> tppMessages) {
    this.tppMessages = tppMessages;
  }

  public Map<String, HrefType> getLinks() {
    return links;
  }

  public void setLinks(Map<String, HrefType> links) {
    this.links = links;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorResponse that = (ErrorResponse) o;
    return Objects.equals(tppMessages, that.tppMessages) &&
        Objects.equals(links, that.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tppMessages,
        links);
  }
}
