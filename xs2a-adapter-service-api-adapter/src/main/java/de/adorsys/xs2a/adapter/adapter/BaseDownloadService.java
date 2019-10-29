package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;

import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.byteArrayResponseHandler;

public class BaseDownloadService extends AbstractService implements DownloadService {
    // TODO take a look at the link: maybe base URI is redundant here
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
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(downloadUrl)
                   .headers(headersMap)
                   .send(requestBuilderInterceptor, byteArrayResponseHandler());
    }
}
