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

package de.adorsys.xs2a.adapter.rest.impl.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * This filter adds optional parameters for successful ING sandbox calls that normally would not be required.
 *
 * ING FAQ https://developer.ing.com/openbanking/support
 * <pre>
 *     I am using the Sandbox, but I am getting a 404 error. What is wrong ?
 *     You are getting this error because your request does not exactly match the requests that are simulated in the Sandbox.
 *     When, for example, the query parameters or their values differ from what is being simulated, you will get a 404.
 *     Make sure you use the exact same query parameters and values as used in the Get started documentation.
 * </pre>
 */
@Component
@Profile("sandbox")
public class IngOptionalParametersFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {
        IngRequestWrapper request = new IngRequestWrapper(req);
        chain.doFilter(request, res);
    }

    private static class IngRequestWrapper extends HttpServletRequestWrapper {

        private static final Map<String, String[]> parameters = new HashMap<>(2);
        static {
            parameters.put("limit", new String[]{"10"});
            parameters.put("balanceTypes", new String[]{"interimBooked"});
        }

        IngRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> parameterMap = new HashMap<>(super.getParameterMap());
            parameterMap.putAll(parameters);
            return parameterMap;
        }

        @Override
        public String getParameter(String name) {
            String[] parameterValues = getParameterValues(name);
            return parameterValues == null ? null : parameterValues[0];
        }

        @Override
        public String[] getParameterValues(String name) {
            return getParameterMap().get(name);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(getParameterMap().keySet());
        }
    }
}


