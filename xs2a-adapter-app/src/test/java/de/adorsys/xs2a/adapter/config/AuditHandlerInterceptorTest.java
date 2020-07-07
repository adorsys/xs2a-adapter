package de.adorsys.xs2a.adapter.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

class AuditHandlerInterceptorTest {

    private static final String URI = "/v1/accounts/E9A850CBB0D24C98A612C6209AF54E2B/transactions";
    private static final String METHOD = "GET";
    private static final String OPERATION = "operation";

    @Test
    void preHandleSanitized() {
        AuditHandlerInterceptor interceptor = new AuditHandlerInterceptor(true);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        doReturn(URI).when(request).getRequestURI();
        doReturn(METHOD).when(request).getMethod();

        interceptor.preHandle(request, response, new Object());

        assertThat(MDC.get(OPERATION)).isEqualTo(METHOD + " /v1/accounts/******/transactions");
    }

    @Test
    void preHandleNotSanitized() {
        AuditHandlerInterceptor interceptor = new AuditHandlerInterceptor();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        doReturn(URI).when(request).getRequestURI();
        doReturn(METHOD).when(request).getMethod();

        interceptor.preHandle(request, response, new Object());

        assertThat(MDC.get(OPERATION)).isEqualTo(METHOD + " " + URI);
    }
}
