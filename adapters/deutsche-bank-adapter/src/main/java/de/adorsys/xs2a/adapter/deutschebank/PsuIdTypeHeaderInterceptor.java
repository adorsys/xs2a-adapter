package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.Request;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Sets an appropriate PSU-ID-Type header value based on the country code and
 * business entity specified in the request uri.
 * https://xs2a.db.com/{service-group}/{country-code}/{business-entity}/{version}/{service}{?query-parameters}
 */
public class PsuIdTypeHeaderInterceptor implements Request.Builder.Interceptor {
    @Override
    public void accept(Request.Builder builder) {
        if (builder.headers().get(RequestHeaders.PSU_ID) != null
            && builder.headers().get(RequestHeaders.PSU_ID_TYPE) == null) {

            setPsuIdType(builder);
        }
    }

    private void setPsuIdType(Request.Builder builder) {
        URI uri = URI.create(builder.uri());
        Path path = Paths.get(uri.getPath());
        if (path.getNameCount() < 3) {
            return;
        }
        String countryCode = path.getName(1).toString();
        if ("DE".equals(countryCode)) {
            String businessEntity = path.getName(2).toString();
            // https://jira.adorsys.de/browse/XS2AAD-669
            if ("DB".equals(businessEntity) || "PFB".equals(businessEntity)) {
                builder.header(RequestHeaders.PSU_ID_TYPE, "DE_ONLB_DB");
            } else if ("Postbank".equals(businessEntity)) {
                builder.header(RequestHeaders.PSU_ID_TYPE, "DE_ONLB_POBA");
            } else if ("Noris".equals(businessEntity)) {
                builder.header(RequestHeaders.PSU_ID_TYPE, "DE_ONLB_NORIS");
            }
        }
    }
}
