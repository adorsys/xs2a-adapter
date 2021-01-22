package de.adorsys.xs2a.adapter.remote.client;

import de.adorsys.xs2a.adapter.rest.api.EmbeddedPreAuthorisationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "embedded-pre-authorisation-client", url = "${xs2a-adapter.url}")
public interface EmbeddedPreAuthorisationClient extends EmbeddedPreAuthorisationApi {
}
