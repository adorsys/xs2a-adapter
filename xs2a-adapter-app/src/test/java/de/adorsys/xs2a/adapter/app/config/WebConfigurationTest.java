package de.adorsys.xs2a.adapter.app.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WebConfigurationTest {

    @Test
    void addInterceptors() {

        TestInterceptorRegistry registry = new TestInterceptorRegistry();

        new WebConfiguration() {
            @Override
            public void addInterceptors(InterceptorRegistry interceptorRegistry) {
                super.addInterceptors(interceptorRegistry);
            }
        }.addInterceptors(registry);

        assertThat(registry.interceptors()).hasSize(1);
        assertThat(registry.interceptors().get(0)).isExactlyInstanceOf(AuditHandlerInterceptor.class);
    }

    private static class TestInterceptorRegistry extends InterceptorRegistry {
        public List<Object> interceptors() {
            return getInterceptors();
        }
    }
}
