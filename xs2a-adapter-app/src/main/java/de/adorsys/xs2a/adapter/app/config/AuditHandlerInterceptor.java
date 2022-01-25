/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.app.config;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.impl.http.Xs2aHttpLogSanitizer;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuditHandlerInterceptor extends HandlerInterceptorAdapter {

    private final HttpLogSanitizer logSanitizer = new Xs2aHttpLogSanitizer();
    private final boolean sanitized;

    public AuditHandlerInterceptor() {
        this(false);
    }

    public AuditHandlerInterceptor(boolean sanitized) {
        this.sanitized = sanitized;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String uri = request.getRequestURI();
        String requestURI = sanitized ? logSanitizer.sanitize(uri) : uri;
        MDC.put("operation", request.getMethod() + " " + requestURI);

        String correlationId = request.getHeader(RequestHeaders.CORRELATION_ID);
        if (correlationId != null) {
            MDC.put("correlationId", correlationId);
        }

        String bankCode = request.getHeader(RequestHeaders.X_GTW_BANK_CODE);
        if (bankCode != null) {
            MDC.put("bankCode", bankCode);
        }

        String aspspId = request.getHeader(RequestHeaders.X_GTW_ASPSP_ID);
        if (aspspId != null) {
            MDC.put("aspspId", aspspId);
        }

        return true;
    }

    @Override
    public
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String approach = response.getHeader(ResponseHeaders.ASPSP_SCA_APPROACH);
        if (approach != null) {
            MDC.put("approach", approach);
        }

        MDC.clear();
    }
}
