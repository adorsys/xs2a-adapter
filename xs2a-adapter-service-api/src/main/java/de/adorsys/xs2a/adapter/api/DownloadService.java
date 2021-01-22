package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.validation.DownloadValidationService;

public interface DownloadService extends DownloadValidationService {

    Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders);
}
