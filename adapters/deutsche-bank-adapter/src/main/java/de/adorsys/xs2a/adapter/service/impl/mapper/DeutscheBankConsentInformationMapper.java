package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.ais.ConsentInformation;
import de.adorsys.xs2a.adapter.service.impl.model.DeutscheBankConsentInformation;
import org.mapstruct.Mapper;

@Mapper
public interface DeutscheBankConsentInformationMapper {
    ConsentInformation toConsentInformation(DeutscheBankConsentInformation deutscheBankConsentInformation);
}
