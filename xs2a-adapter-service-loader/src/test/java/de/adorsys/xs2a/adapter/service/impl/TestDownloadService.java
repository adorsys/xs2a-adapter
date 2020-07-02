package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;

public class TestDownloadService implements DownloadService {

    @Override
    public Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders) {
        return null;
    }
}
