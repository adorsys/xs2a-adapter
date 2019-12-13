package de.adorsys.xs2a.adapter.config;

import de.adorsys.bg.monitoring.client.MonitoringContext;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MonitoringHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MonitoringContext.setBankCode(request.getHeader(RequestHeaders.X_GTW_BANK_CODE));
        return true;
    }
}
