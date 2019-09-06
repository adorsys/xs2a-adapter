package de.adorsys.xs2a.adapter.api.remote;

import de.adorsys.xs2a.adapter.api.Oauth2Api;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "oauth2-client", url = "${xs2a-adapter.url}")
public interface Oauth2Client extends Oauth2Api {
}
