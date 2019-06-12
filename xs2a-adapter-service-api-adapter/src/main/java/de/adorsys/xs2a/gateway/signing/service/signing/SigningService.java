package de.adorsys.xs2a.gateway.signing.service.signing;

import java.nio.charset.Charset;
import java.security.PrivateKey;

public interface SigningService {

    byte[] sign(PrivateKey privateKey, String data, Charset charset);
}
