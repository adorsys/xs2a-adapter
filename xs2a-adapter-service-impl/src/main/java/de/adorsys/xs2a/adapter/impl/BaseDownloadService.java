package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.StringUri;

import java.util.Map;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;
import static de.adorsys.xs2a.adapter.impl.http.ResponseHandlers.byteArrayResponseHandler;

public class BaseDownloadService extends AbstractService implements DownloadService {
    private static final String HTTPS_PROTOCOL = "https://";

    protected final String baseUri;
    private final Request.Builder.Interceptor requestBuilderInterceptor;

    public BaseDownloadService(String baseUri, HttpClient httpClient) {
        this(baseUri, httpClient, null);
    }

    public BaseDownloadService(String baseUri,
                               HttpClient httpClient,
                               Request.Builder.Interceptor requestBuilderInterceptor) {
        super(httpClient);
        this.baseUri = baseUri;
        this.requestBuilderInterceptor = requestBuilderInterceptor;
    }

    @Override
    public Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders) {
        requireValid(validateDownload(downloadUrl, requestHeaders));

        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        Response<byte[]> response = httpClient.get(modifyDownloadUrl(downloadUrl))
                                    .headers(headersMap)
                                    .send(requestBuilderInterceptor, byteArrayResponseHandler());

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
