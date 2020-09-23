package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID_TYPE;

public class PsuIdTypeHeaderInterceptor implements Interceptor {

    @Override
    public Request.Builder preHandle(Request.Builder builder) {
        return handlePsuIdTypeHeader(builder);
    }

    private Request.Builder handlePsuIdTypeHeader(Request.Builder builder) {
        if (builder.headers().containsKey(PSU_ID_TYPE)) {
            String psuIdType = builder.headers().get(PSU_ID_TYPE);
            if (StringUtils.isBlank(psuIdType)) {
                builder.headers().remove(PSU_ID_TYPE);
            }
        }

        return builder;
    }
}
