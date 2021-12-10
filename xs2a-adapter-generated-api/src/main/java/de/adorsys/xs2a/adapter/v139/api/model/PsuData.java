package de.adorsys.xs2a.adapter.v139.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class PsuData {
    private String password;

    private String encryptedPassword;

    private String additionalPassword;

    private String additionalEncryptedPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getAdditionalPassword() {
        return additionalPassword;
    }

    public void setAdditionalPassword(String additionalPassword) {
        this.additionalPassword = additionalPassword;
    }

    public String getAdditionalEncryptedPassword() {
        return additionalEncryptedPassword;
    }

    public void setAdditionalEncryptedPassword(String additionalEncryptedPassword) {
        this.additionalEncryptedPassword = additionalEncryptedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PsuData that = (PsuData) o;
        return Objects.equals(password, that.password) &&
            Objects.equals(encryptedPassword, that.encryptedPassword) &&
            Objects.equals(additionalPassword, that.additionalPassword) &&
            Objects.equals(additionalEncryptedPassword, that.additionalEncryptedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password,
            encryptedPassword,
            additionalPassword,
            additionalEncryptedPassword);
    }
}
