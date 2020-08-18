package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;

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
