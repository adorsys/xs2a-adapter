package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.validation.DownloadValidationService;

public interface DownloadService extends DownloadValidationService {

    Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders);
}
