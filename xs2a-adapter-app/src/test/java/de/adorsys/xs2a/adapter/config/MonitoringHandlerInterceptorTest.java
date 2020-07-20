package de.adorsys.xs2a.adapter.config;

import de.adorsys.bg.monitoring.client.MonitoringContext;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class MonitoringHandlerInterceptorTest {

    @Test
    void preHandle() {
        String expectedBankCode = "bank-code";
        MonitoringHandlerInterceptor interceptor = new MonitoringHandlerInterceptor();
        HttpServletRequest request = mock(HttpServletRequest.class);

        doReturn(expectedBankCode).when(request).getHeader(RequestHeaders.X_GTW_BANK_CODE);

        boolean preHandle = interceptor.preHandle(request, null, null);

        assertThat(preHandle).isTrue();
        assertThat(MonitoringContext.getBankCode()).isEqualTo(expectedBankCode);
    }
}
