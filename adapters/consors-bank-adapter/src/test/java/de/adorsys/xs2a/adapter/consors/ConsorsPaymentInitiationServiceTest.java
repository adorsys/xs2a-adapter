package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.PaymentInitiationJson;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsorsPaymentInitiationServiceTest {

    @InjectMocks
    private ConsorsPaymentInitiationService service;
    @Mock
    private HttpClient client;
    @Captor
    private ArgumentCaptor<Request.Builder> builderCaptor;

    @BeforeEach
    void setUp() {
    }

    @Test
    void initiatePayment_noPsuId() {

        service.initiatePayment(PaymentService.PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            RequestHeaders.empty(),
            RequestParams.empty(),
            new PaymentInitiationJson());
    }

    @Test
    void addPsuIdHeader() {
    }
}
