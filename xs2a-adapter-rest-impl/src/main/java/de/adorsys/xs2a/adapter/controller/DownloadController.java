package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DownloadController {
    private static final String V1_DOWNLOAD = "/v1/download";

    private final DownloadService downloadService;
    private final HeadersMapper headersMapper;

    public DownloadController(DownloadService downloadService, HeadersMapper headersMapper) {
        this.downloadService = downloadService;
        this.headersMapper = headersMapper;
    }

    @GetMapping(V1_DOWNLOAD)
    public ResponseEntity<byte[]> download(@RequestParam("url") String downloadUrl,
                                           @RequestHeader Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        Response<byte[]> response = downloadService.download(downloadUrl, requestHeaders);

        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }
}
