package de.adorsys.xs2a.adapter.signing.service.encoding;

import java.util.Base64;

public class Base64EncodingService implements EncodingService {

    @Override
    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
