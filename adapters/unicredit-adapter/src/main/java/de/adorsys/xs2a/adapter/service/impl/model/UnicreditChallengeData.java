package de.adorsys.xs2a.adapter.service.impl.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import de.adorsys.xs2a.adapter.service.OtpFormat;

import java.util.List;

public class UnicreditChallengeData {
    private byte[] image;
    private List<String> data;
    private String imageLink;
    @JsonAlias("optMaxLength")
    private Integer otpMaxLength;
    @JsonAlias("optFormat")
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
}
