package de.adorsys.xs2a.adapter.service;

public interface DownloadService {

    Response<byte[]> download(String downloadUrl, RequestHeaders requestHeaders);
}
