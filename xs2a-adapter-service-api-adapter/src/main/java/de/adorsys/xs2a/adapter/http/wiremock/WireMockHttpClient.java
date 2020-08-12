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

package de.adorsys.xs2a.adapter.http.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import org.apache.http.impl.client.CloseableHttpClient;

import java.security.SecureRandom;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockHttpClient extends ApacheHttpClient {

    private static final String URL_REGEX = "https?://[^/]+";
    private WireMockServer wireMockServer;
    private String wireMockUrl;

    public WireMockHttpClient(String adapterId, CloseableHttpClient httpClient) {
        this(adapterId, httpClient, randomPort());
    }

    public WireMockHttpClient(String adapterId, CloseableHttpClient httpClient, int wireMockPort) {
        super(httpClient);
        WireMockConfiguration options = options()
            .port(wireMockPort)
            .extensions(new ResponseTemplateTransformer(true))
            .fileSource(new JarReadingClasspathFileSource(adapterId));

        wireMockServer = new WireMockServer(options);
        wireMockServer.start();
        wireMockUrl = "http://localhost:" + wireMockServer.port();
    }

    public int getWireMockPort() {
        return wireMockServer.port();
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
