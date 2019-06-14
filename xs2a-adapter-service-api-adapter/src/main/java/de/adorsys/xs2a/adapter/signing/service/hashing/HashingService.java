package de.adorsys.xs2a.adapter.signing.service.hashing;

import java.nio.charset.Charset;

public interface HashingService {

    byte[] hash(String data, Charset charset);
}
