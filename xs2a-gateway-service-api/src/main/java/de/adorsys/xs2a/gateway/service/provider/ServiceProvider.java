package de.adorsys.xs2a.gateway.service.provider;

import java.util.Set;

public interface ServiceProvider {

    Set<String> getBankCodes();

    String getBankName();
}
