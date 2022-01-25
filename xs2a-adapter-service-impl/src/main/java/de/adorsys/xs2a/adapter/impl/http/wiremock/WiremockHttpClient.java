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

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WiremockHttpClient extends ApacheHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(WiremockHttpClient.class);

    private static final String URL_REGEX = "https?://[^/]+";
    private WireMockServer wireMockServer;
    private final String wireMockUrl;

    public WiremockHttpClient(String adapterId, CloseableHttpClient httpClient) {
        this(adapterId, httpClient, randomPort(), null, null);
    }

    public WiremockHttpClient(String adapterId, CloseableHttpClient httpClient, HttpLogSanitizer logSanitizer) {
        this(adapterId, httpClient, randomPort(), logSanitizer, null);
    }

    public WiremockHttpClient(String adapterId, CloseableHttpClient httpClient,
                              HttpLogSanitizer logSanitizer, String wiremockStandaloneUrl) {
        this(adapterId, httpClient, randomPort(), logSanitizer, wiremockStandaloneUrl);
    }

    public WiremockHttpClient(String adapterId, CloseableHttpClient httpClient, int wireMockPort,
                              HttpLogSanitizer logSanitizer, String wiremockStandaloneUrl) {
        super(logSanitizer, httpClient);

        if (StringUtils.isNotEmpty(wiremockStandaloneUrl)) {
            wireMockUrl = wiremockStandaloneUrl;
            logger.info("Wiremock [standalone] server is connected: {}", wireMockUrl);
        } else {
            WireMockConfiguration options = options()
                                                .port(wireMockPort)
                                                .extensions(new ResponseTemplateTransformer(true))
                                                .fileSource(new JarReadingClasspathFileSource(adapterId));

            wireMockServer = new WireMockServer(options);
            wireMockServer.start();
            wireMockUrl = "http://localhost:" + wireMockServer.port();
            logger.info("Wiremock [local] server is up and running: {}", wireMockUrl);
        }
    }

    @Override
    public Request.Builder get(String uri) {
        return super.get(rewriteUrl(uri));
    }

    @Override
    public Request.Builder post(String uri) {
        return super.post(rewriteUrl(uri));
    }

    @Override
    public Request.Builder put(String uri) {
        return super.put(rewriteUrl(uri));
    }

    @Override
    public Request.Builder delete(String uri) {
        return super.delete(rewriteUrl(uri));
    }

    public static int randomPort() {
        return new SecureRandom().nextInt((2 << 15) - 9000) + 9000;
    }

    private String rewriteUrl(String sourceUrl) {
        return sourceUrl.replaceFirst(URL_REGEX, wireMockUrl);
    }
}
