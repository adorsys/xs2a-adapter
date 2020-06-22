package de.adorsys.xs2a.adapter.remote.api;

import de.adorsys.xs2a.adapter.rest.api.Oauth2Api;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "oauth2-client", url = "${xs2a-adapter.url}")
public interface Oauth2Client extends Oauth2Api {
}
