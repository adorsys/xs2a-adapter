package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SparkassePaymentInitiationServiceTest {

    private final HttpClient client = mock(HttpClient.class);
    private final ArgumentCaptor<Request.Builder> builderCaptor =
        ArgumentCaptor.forClass(Request.Builder.class);
    private final PaymentInitiationService service = new SparkasseServiceProvider()
        .getPaymentInitiationService(new Aspsp(), (x, y, z) -> client, null, new IdentityLinksRewriter());

    @Test
    void initiatePayment() {
        when(client.post(anyString()))
            .thenReturn(new RequestBuilderImpl(client, "POST", "https://example.com"));
        when(client.send(any(), any()))
            .thenReturn(new Response<>(-1, new PaymentInitationRequestResponse201(), null));

        String body =  "<Document>\n" +
            "    <CstmrCdtTrfInitn>\n" +
            "        <PmtInf>\n" +
            "            <ReqdExctnDt>2020-07-10</ReqdExctnDt>\n" +
            "        </PmtInf>\n" +
            "        <PmtInf>\n" +
            "            <ReqdExctnDt>2020-07-10</ReqdExctnDt>\n" +
            "        </PmtInf>\n" +
            "    </CstmrCdtTrfInitn>\n" +
            "</Document>";

        service.initiatePayment(PaymentService.PAYMENTS,
            PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            RequestParams.empty(),
            body);

        verify(client, times(1)).send(builderCaptor.capture(), any());

        Request.Builder actualBuilder = builderCaptor.getValue();
        assertThat(actualBuilder)
            .isNotNull()
            .extracting(Request.Builder::body)
            .asString()
            .isXmlEqualTo("<Document>\n" +
                "    <CstmrCdtTrfInitn>\n" +
                "        <PmtInf>\n" +
                "            <ReqdExctnDt>1999-01-01</ReqdExctnDt>\n" +
                "        </PmtInf>\n" +
                "        <PmtInf>\n" +
                "            <ReqdExctnDt>1999-01-01</ReqdExctnDt>\n" +
                "        </PmtInf>\n" +
                "    </CstmrCdtTrfInitn>\n" +
                "</Document>");
    }
}
