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

import org.apache.commons.fileupload.util.mime.MimeUtility;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@ConditionalOnProperty(prefix = "xs2a-adapter.rest", name = "mime-headers-support-enabled", havingValue = "true", matchIfMissing = true)
@Component
public class MimeHeadersSupportFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {

        HttpServletRequestWrapper request = new MimeHeaderRequestWrapper(req);
        chain.doFilter(request, res);
    }

    private static class MimeHeaderRequestWrapper extends HttpServletRequestWrapper {
        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request the {@link HttpServletRequest} to be wrapped.
         * @throws IllegalArgumentException if the request is null
         */
        MimeHeaderRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getHeader(String name) {
            String header = super.getHeader(name);
            return decodeHeader(header);
        }

        private String decodeHeader(String header) {
            if (header == null) {
                return null;
            }

            try {
                return MimeUtility.decodeText(header);
            } catch (UnsupportedEncodingException e) {
                return header;
            }
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            Enumeration<String> headers = super.getHeaders(name);
            List<String> decodedHeaders = new ArrayList<>();
            while (headers.hasMoreElements()) {
                String s = headers.nextElement();
                decodedHeaders.add(decodeHeader(s));
            }
            return Collections.enumeration(decodedHeaders);
        }
    }
}
