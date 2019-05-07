package de.adorsys.xs2a.gateway.service.provider;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public interface Sparkasse {
    Set<String> BANK_CODES = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("99999999")));

    String BASE_URI = "https://xs2a-sandbox.f-i-apim.de:8444/fixs2a-env/xs2a-api/12345678/v1";
}
