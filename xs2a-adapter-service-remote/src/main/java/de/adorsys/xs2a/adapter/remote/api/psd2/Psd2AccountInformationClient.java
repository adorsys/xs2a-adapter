package de.adorsys.xs2a.adapter.remote.api.psd2;

import de.adorsys.xs2a.adapter.rest.psd2.Psd2AccountInformationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @deprecated
 * This client is no longer acceptable and will be removed in future releases.
 * <p>Use {@link de.adorsys.xs2a.adapter.remote.api.AccountInformationClient} instead.</p>
 */
@Deprecated
@FeignClient(name = "psd2-account-information-client", url = "${xs2a-adapter.url}")
public interface Psd2AccountInformationClient extends Psd2AccountInformationApi {
}
