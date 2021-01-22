package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;

public class TestDownloadService implements DownloadService {

    @Override
    public Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders) {
        return null;
    }
}
