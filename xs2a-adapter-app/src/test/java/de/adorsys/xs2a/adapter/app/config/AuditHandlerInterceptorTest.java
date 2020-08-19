package de.adorsys.xs2a.adapter.app.config;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuditHandlerInterceptorTest {

    private static final String URI = "/v1/accounts/E9A850CBB0D24C98A612C6209AF54E2B/transactions";
    private static final String METHOD = "GET";
    private static final String OPERATION = "operation";

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final AuditHandlerInterceptor interceptor = new AuditHandlerInterceptor();

    @Test
    void preHandleSanitized() {
        AuditHandlerInterceptor interceptor = new AuditHandlerInterceptor(true);
        HttpServletResponse response = mock(HttpServletResponse.class);

        doReturn(URI).when(request).getRequestURI();
        doReturn(METHOD).when(request).getMethod();

        interceptor.preHandle(request, response, new Object());

        assertThat(MDC.get(OPERATION)).isEqualTo(METHOD + " /v1/accounts/******/transactions");
    }

    @Test
    void preHandleNotSanitized() {
        HttpServletResponse response = mock(HttpServletResponse.class);

        doReturn(URI).when(request).getRequestURI();
        doReturn(METHOD).when(request).getMethod();

        interceptor.preHandle(request, response, new Object());

        assertThat(MDC.get(OPERATION)).isEqualTo(METHOD + " " + URI);
    }

    @Test
    void preHandle() {

        String uri = "/request/uri";
        String method = "GET";
        String correlationId = "correlation-id";
        String bankCode = "bank-code";
        String aspsId = "aspsp-id";

        doReturn(uri).when(request).getRequestURI();
        doReturn(method).when(request).getMethod();
        doReturn(correlationId).when(request).getHeader(RequestHeaders.CORRELATION_ID);
        doReturn(bankCode).when(request).getHeader(RequestHeaders.X_GTW_BANK_CODE);
        doReturn(aspsId).when(request).getHeader(RequestHeaders.X_GTW_ASPSP_ID);

        boolean preHandle = interceptor.preHandle(request, null, null);

        assertThat(preHandle).isTrue();
        assertThat(MDC.get("operation")).isEqualTo(method + " " + uri);
        assertThat(MDC.get("correlationId")).isEqualTo(correlationId);
        assertThat(MDC.get("bankCode")).isEqualTo(bankCode);
        assertThat(MDC.get("aspspId")).isEqualTo(aspsId);
    }

    @Test
    void afterCompletion() {
        String approach = "approach";
        HttpServletResponse response = mock(HttpServletResponse.class);

        doReturn(approach).when(response).getHeader(ResponseHeaders.ASPSP_SCA_APPROACH);

        interceptor.afterCompletion(request, response, null, null);

        verify(response, times(1)).getHeader(ResponseHeaders.ASPSP_SCA_APPROACH);

        assertThat(MDC.getCopyOfContextMap()).isNull();
    }
}
