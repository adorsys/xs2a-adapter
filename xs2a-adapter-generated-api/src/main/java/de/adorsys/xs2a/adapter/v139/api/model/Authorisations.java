package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class Authorisations {
  private List<String> authorisationIds;

  public List<String> getAuthorisationIds() {
    return authorisationIds;
  }

  public void setAuthorisationIds(List<String> authorisationIds) {
    this.authorisationIds = authorisationIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Authorisations that = (Authorisations) o;
    return Objects.equals(authorisationIds, that.authorisationIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorisationIds);
  }
}
