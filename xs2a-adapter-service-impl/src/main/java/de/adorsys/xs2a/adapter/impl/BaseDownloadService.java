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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.StringUri;

import java.util.Collections;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;

public class BaseDownloadService extends AbstractService implements DownloadService {
    private static final String HTTPS_PROTOCOL = "https://";

    protected final String baseUri;
    private final Interceptor requestBuilderInterceptor;
    private final ResponseHandlers responseHandlers;

    public BaseDownloadService(String baseUri, HttpClient httpClient) {
        this(baseUri, httpClient, null);
    }

    public BaseDownloadService(String baseUri, HttpClient httpClient, HttpLogSanitizer logSanitizer) {
        this(baseUri, httpClient, null, logSanitizer);
    }

    public BaseDownloadService(String baseUri,
                               HttpClient httpClient,
                               Interceptor requestBuilderInterceptor,
                               HttpLogSanitizer logSanitizer) {
        super(httpClient);
        this.baseUri = baseUri;
        this.requestBuilderInterceptor = requestBuilderInterceptor;
        this.responseHandlers = new ResponseHandlers(logSanitizer);
    }

    @Override
    public Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders) {
        requireValid(validateDownload(downloadUrl, requestHeaders));

        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        Response<byte[]> response = httpClient.get(modifyDownloadUrl(downloadUrl))
                                        .headers(headersMap)
                                        .send(responseHandlers.byteArrayResponseHandler(), Collections.singletonList(requestBuilderInterceptor));

        return new Response<>(
            response.getStatusCode(),
            response.getBody(),
            modifyResponseHeaders(response.getHeaders())
        );
    }

    protected String modifyDownloadUrl(String downloadUrl) {
        if (StringUri.isUri(downloadUrl)) {
            return StringUri.fromElements(baseUri, downloadUrl);
        }

        if (!StringUri.containsProtocol(downloadUrl)) {
            return StringUri.fromElements(HTTPS_PROTOCOL, downloadUrl);
        }

        return downloadUrl;
    }

    protected ResponseHeaders modifyResponseHeaders(ResponseHeaders responseHeaders) {
        Map<String, String> headersMap = responseHeaders.getHeadersMap();
        headersMap.put(ResponseHeaders.CONTENT_TYPE, "application/octet-stream");
        return ResponseHeaders.fromMap(headersMap);
    }
}
