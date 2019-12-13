package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AddressTO;
import de.adorsys.xs2a.adapter.service.model.Address;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressMapperTest {
    private static final String POSTAL_CODE = "postalCode";
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    private static final String STREET = "street";
    private static final String BUILDING_NUMBER = "buildingNumber";

    @Test
    public void toAddressTO() {
        AddressTO addressTO = Mappers.getMapper(AddressMapper.class).toAddressTO(buildAddress());

        assertThat(addressTO).isNotNull();
        assertThat(addressTO.getPostCode()).isEqualTo(POSTAL_CODE);
        assertThat(addressTO.getCountry()).isEqualTo(COUNTRY);
        assertThat(addressTO.getTownName()).isEqualTo(CITY);
        assertThat(addressTO.getStreetName()).isEqualTo(STREET);
        assertThat(addressTO.getBuildingNumber()).isEqualTo(BUILDING_NUMBER);
    }

    static Address buildAddress() {
        Address address = new Address();

        address.setPostCode(POSTAL_CODE);
        address.setCountry(COUNTRY);
        address.setTownName(CITY);
        address.setStreetName(STREET);
        address.setBuildingNumber(BUILDING_NUMBER);

        return address;
    }
}
