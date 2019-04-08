package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.AddressTO;
import de.adorsys.xs2a.gateway.service.Address;
import org.junit.Test;
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
        assertThat(addressTO.getPostalCode()).isEqualTo(POSTAL_CODE);
        assertThat(addressTO.getCountry()).isEqualTo(COUNTRY);
        assertThat(addressTO.getCity()).isEqualTo(CITY);
        assertThat(addressTO.getStreet()).isEqualTo(STREET);
        assertThat(addressTO.getBuildingNumber()).isEqualTo(BUILDING_NUMBER);
    }

    static Address buildAddress() {
        Address address = new Address();

        address.setPostalCode(POSTAL_CODE);
        address.setCountry(COUNTRY);
        address.setCity(CITY);
        address.setStreet(STREET);
        address.setBuildingNumber(BUILDING_NUMBER);

        return address;
    }
}