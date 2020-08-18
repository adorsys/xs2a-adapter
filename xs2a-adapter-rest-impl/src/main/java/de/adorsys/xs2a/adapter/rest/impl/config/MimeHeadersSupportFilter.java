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
