/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.api.model;

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
