package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;

public class DownloadServiceImpl implements DownloadService {
    private final AdapterServiceLoader adapterServiceLoader;

    public DownloadServiceImpl(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders) {
        return adapterServiceLoader.getDownloadService(requestHeaders)
                   .download(downloadUrl, requestHeaders);
    }
}
