package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class TppMessage201PaymentInitiation {
  private TppMessageCategory category;

  private MessageCode201PaymentInitiation code;

  private String path;

  private String text;

  public TppMessageCategory getCategory() {
    return category;
  }

  public void setCategory(TppMessageCategory category) {
    this.category = category;
  }

  public MessageCode201PaymentInitiation getCode() {
    return code;
  }

  public void setCode(MessageCode201PaymentInitiation code) {
    this.code = code;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TppMessage201PaymentInitiation that = (TppMessage201PaymentInitiation) o;
    return Objects.equals(category, that.category) &&
        Objects.equals(code, that.code) &&
        Objects.equals(path, that.path) &&
        Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category,
        code,
        path,
        text);
  }
}
