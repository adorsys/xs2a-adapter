package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class TppMessage2XX {
    private TppMessageCategory category;

    private MessageCode2XX code;

    private String path;

    private String text;

    public TppMessageCategory getCategory() {
        return category;
    }

    public void setCategory(TppMessageCategory category) {
        this.category = category;
    }

    public MessageCode2XX getCode() {
        return code;
    }

    public void setCode(MessageCode2XX code) {
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
        TppMessage2XX that = (TppMessage2XX) o;
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
