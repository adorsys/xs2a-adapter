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
