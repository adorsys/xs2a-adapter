package de.adorsys.xs2a.adapter.service.provider;

import java.util.Set;

public interface ServiceProvider {

    Set<String> getBankCodes();

    String getBankName();
}
