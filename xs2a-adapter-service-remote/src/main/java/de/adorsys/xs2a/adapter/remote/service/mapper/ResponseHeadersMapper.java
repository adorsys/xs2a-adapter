package de.adorsys.xs2a.adapter.remote.service.mapper;

import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import org.mapstruct.Mapper;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface ResponseHeadersMapper {

    default ResponseHeaders getHeaders(HttpHeaders httpHeaders) {
        Set<Map.Entry<String, List<String>>> entrySet = httpHeaders.entrySet();
        Map<String, String> headers = new HashMap<>(entrySet.size());
        for (Map.Entry<String, List<String>> entry : entrySet) {
            List<String> value = entry.getValue();
            if (value != null && !value.isEmpty()) {
                headers.put(entry.getKey(), value.get(0));
            }
        }
        return ResponseHeaders.fromMap(headers);
    }
}
