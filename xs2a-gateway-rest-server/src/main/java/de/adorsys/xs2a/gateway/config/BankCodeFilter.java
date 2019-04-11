/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.service.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

@Profile("oneBankMode")
@Component
public class BankCodeFilter implements Filter {

    @Value("${one-bank-mode.code}")
    private String bankCode;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (bankCode != null && !bankCode.isEmpty()) {
            MutableHttpServletRequest wrapper = new MutableHttpServletRequest((HttpServletRequest) request);
            wrapper.addHeader(Headers.X_GTW_BANK_CODE, bankCode);
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }


    public final class MutableHttpServletRequest extends HttpServletRequestWrapper {
        // holds custom header and value mapping
        private final Map<String, String> customHeaders;

        MutableHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.customHeaders = new HashMap<>();
        }

        void addHeader(String name, String value) {
            this.customHeaders.put(name, value);
        }


        @Override
        public String getHeader(String name) {
            // check the custom headers first
            String headerValue = customHeaders.get(name);

            if (headerValue != null) {
                return headerValue;
            }
            // else return from into the original wrapped object
            return ((HttpServletRequest) getRequest()).getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            Set<String> set = new HashSet<>();

            if (customHeaders.containsKey(name)) {
                set.add(customHeaders.get(name));
            }

            // now add the headers from the wrapped request object
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaders(name);
            if (e != null) {
                while (e.hasMoreElements()) {
                    // add the names of the request headers into the list
                    set.add(e.nextElement());
                }
            }
            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            // create a set of the custom header names
            Set<String> set = new HashSet<>(customHeaders.keySet());

            // now add the headers from the wrapped request object
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
            while (e.hasMoreElements()) {
                // add the names of the request headers into the list
                String n = e.nextElement();
                set.add(n);
            }

            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }
    }
}
