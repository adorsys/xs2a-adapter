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
                                        .send(responseHandlers.byteArrayResponseHandler(), requestBuilderInterceptor);

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
