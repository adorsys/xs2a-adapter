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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ChallengeData {
    private byte[] image;

    private List<String> data;

    private String imageLink;

    private Integer otpMaxLength;

    private OtpFormat otpFormat;

    private String additionalInformation;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getOtpMaxLength() {
        return otpMaxLength;
    }

    public void setOtpMaxLength(Integer otpMaxLength) {
        this.otpMaxLength = otpMaxLength;
    }

    public OtpFormat getOtpFormat() {
        return otpFormat;
    }

    public void setOtpFormat(OtpFormat otpFormat) {
        this.otpFormat = otpFormat;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChallengeData that = (ChallengeData) o;
        return Objects.equals(image, that.image) &&
            Objects.equals(data, that.data) &&
            Objects.equals(imageLink, that.imageLink) &&
            Objects.equals(otpMaxLength, that.otpMaxLength) &&
            Objects.equals(otpFormat, that.otpFormat) &&
            Objects.equals(additionalInformation, that.additionalInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image,
            data,
            imageLink,
            otpMaxLength,
            otpFormat,
            additionalInformation);
    }

    public enum OtpFormat {
        CHARACTERS("characters"),

        INTEGER("integer");

        private String value;

        OtpFormat(String value) {
            this.value = value;
        }

        @JsonCreator
        public static OtpFormat fromValue(String value) {
            for (OtpFormat e : OtpFormat.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
