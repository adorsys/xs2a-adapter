package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class Address {
    private String streetName;

    private String buildingNumber;

    private String townName;

    private String postCode;

    private String country;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return Objects.equals(streetName, that.streetName) &&
            Objects.equals(buildingNumber, that.buildingNumber) &&
            Objects.equals(townName, that.townName) &&
            Objects.equals(postCode, that.postCode) &&
            Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName,
            buildingNumber,
            townName,
            postCode,
            country);
    }
}
