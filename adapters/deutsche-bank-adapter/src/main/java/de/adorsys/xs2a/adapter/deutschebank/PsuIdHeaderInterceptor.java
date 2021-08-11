package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * For DE/DB and DE/Noris the subaccount number will be removed from the PSU-ID if present.
 */
public class PsuIdHeaderInterceptor implements Interceptor {
    @Override
    public Request.Builder preHandle(Request.Builder builder) {
        String psuId = builder.headers().get(RequestHeaders.PSU_ID);
        if (psuId != null) {
            trimPsuId(builder, psuId);
        }
        return builder;
    }

    private void trimPsuId(Request.Builder builder, String psuId) {
        URI uri = URI.create(builder.uri());
        Path path = Paths.get(uri.getPath());
        if (path.getNameCount() < 3) {
            return;
        }
        String countryCode = path.getName(1).toString();
        if ("DE".equals(countryCode)) {
            String businessEntity = path.getName(2).toString();
            if (("DB".equals(businessEntity) || "Noris".equals(businessEntity))
                && psuId.length() == 12) {
                builder.header(RequestHeaders.PSU_ID, psuId.substring(0, 10));
            }
        }
    }
}
