package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class TppMessageTO {
    private TppMessageCategoryTO category;

    private MessageCodeTO code;

    private String path;

    private String text;

    public TppMessageCategoryTO getCategory() {
        return category;
    }

    public void setCategory(TppMessageCategoryTO category) {
        this.category = category;
    }

    public MessageCodeTO getCode() {
        return code;
    }

    public void setCode(MessageCodeTO code) {
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
