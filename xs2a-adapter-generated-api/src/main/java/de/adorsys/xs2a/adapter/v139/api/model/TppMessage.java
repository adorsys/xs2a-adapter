package de.adorsys.xs2a.adapter.v139.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class TppMessage {
    private TppMessageCategory category;

    private String code;

    private String path;

    private String text;

    public TppMessageCategory getCategory() {
        return category;
    }

    public void setCategory(TppMessageCategory category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        TppMessage that = (TppMessage) o;
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
