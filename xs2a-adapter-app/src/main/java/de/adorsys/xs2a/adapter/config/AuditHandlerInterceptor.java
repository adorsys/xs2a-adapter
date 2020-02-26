package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuditHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MDC.put("operation", request.getMethod() + " " + request.getRequestURI());

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
