package de.adorsys.xs2a.adapter.api.validation;

import de.adorsys.xs2a.adapter.api.RequestHeaders;

import java.util.Collections;
import java.util.List;

public interface DownloadValidationService {
    default List<ValidationError> validateDownload(String downloadUrl, RequestHeaders requestHeaders) {
        return Collections.emptyList();
    }
}
