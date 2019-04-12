package de.adorsys.xs2a.gateway.service.impl.mapper;

import de.adorsys.xs2a.gateway.service.ais.ConsentInformation;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankConsentInformation;
import org.mapstruct.Mapper;

@Mapper
public interface DeutscheBankConsentInformationMapper {
    ConsentInformation toConsentInformation(DeutscheBankConsentInformation deutscheBankConsentInformation);
}
