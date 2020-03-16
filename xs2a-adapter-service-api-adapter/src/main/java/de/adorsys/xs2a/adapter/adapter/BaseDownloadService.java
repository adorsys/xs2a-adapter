package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;

import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.byteArrayResponseHandler;

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
