package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AddressTO;
import de.adorsys.xs2a.adapter.service.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressTO toAddressTO(Address address);

    Address toAddress(AddressTO to);
}
