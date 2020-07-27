package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class SparkassePaymentInitiationServiceTest {

    private final HttpClient httpClient = Mockito.spy(AbstractHttpClient.class);
    private final PaymentInitiationService paymentInitiationService = new SparkasseServiceProvider()
        .getPaymentInitiationService(new Aspsp(), (x, y, z) -> httpClient, null, new IdentityLinksRewriter());

    @Test
    void reqdExctnDtRemovedFrom() {
        Mockito.doReturn(new Response<>(201, new PaymentInitationRequestResponse201(), null))
            .when(httpClient).send(Mockito.any(), Mockito.any());

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS.toString(),
            PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS.toString(),
            RequestHeaders.empty(),
            RequestParams.empty(),
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\">\n" +
                "    <CstmrCdtTrfInitn>\n" +
                "        <PmtInf>\n" +
                "            <ReqdExctnDt>2017-02-15</ReqdExctnDt>\n" +
                "        </PmtInf>\n" +
                "        <PmtInf>\n" +
                "            <ReqdExctnDt>2017-02-15</ReqdExctnDt>\n" +
                "        </PmtInf>\n" +
                "    </CstmrCdtTrfInitn>\n" +
                "</Document>");

        ArgumentCaptor<Request.Builder> requestBuilderArgumentCaptor = ArgumentCaptor.forClass(Request.Builder.class);
        Mockito.verify(httpClient).send(requestBuilderArgumentCaptor.capture(), Mockito.any());
        assertThat(requestBuilderArgumentCaptor.getValue().body())
            .isEqualTo("<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.03\">\n" +
                "    <CstmrCdtTrfInitn>\n" +
                "        <PmtInf>\n" +
                "        </PmtInf>\n" +
                "        <PmtInf>\n" +
                "        </PmtInf>\n" +
                "    </CstmrCdtTrfInitn>\n" +
                "</Document>");
    }
}
