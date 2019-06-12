package de.adorsys.xs2a.gateway.model;

import javax.annotation.Generated;

@Generated("xs2a-gateway-codegen")
public class TppMessage2XXTO {
  private TppMessageCategoryTO category;

  private MessageCode2XXTO code;

  private String path;

  private String text;

  public TppMessageCategoryTO getCategory() {
    return category;
  }

  public void setCategory(TppMessageCategoryTO category) {
    this.category = category;
  }

  public MessageCode2XXTO getCode() {
    return code;
  }

  public void setCode(MessageCode2XXTO code) {
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
}
