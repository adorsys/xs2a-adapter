package de.adorsys.xs2a.adapter.api.http;

public interface HttpClientFactory {
    HttpClient getHttpClient(String adapterId, String qwacAlias, String[] supportedCipherSuites, HttpLogSanitizer logSanitiser);

    default HttpClient getHttpClient(String adapterId, String qwacAlias, HttpLogSanitizer logSanitiser) {
        return getHttpClient(adapterId, qwacAlias, null, logSanitiser);
    }

    default HttpClient getHttpClient(String adapterId, HttpLogSanitizer logSanitiser) {
        return getHttpClient(adapterId, null, logSanitiser);
    }
}
