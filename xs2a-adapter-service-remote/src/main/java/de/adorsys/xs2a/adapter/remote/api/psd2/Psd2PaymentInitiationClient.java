package de.adorsys.xs2a.adapter.remote.api.psd2;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "psd2-payment-initiation-client", url = "${xs2a-adapter.url}")
public interface Psd2PaymentInitiationClient extends Psd2PaymentApi {
}
