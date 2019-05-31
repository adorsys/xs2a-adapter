package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.AddressTO;
import de.adorsys.xs2a.gateway.service.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressTO toAddressTO(Address address);
}
