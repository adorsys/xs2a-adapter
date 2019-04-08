package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.pis.PaymentInitiationStatusResponse200Json;
import de.adorsys.xs2a.gateway.service.PaymentInitiationStatus;
import de.adorsys.xs2a.gateway.service.TransactionStatus;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentInitiationStatusMapperTest {

    private PaymentInitiationStatusMapper paymentInitiationStatusMapper = Mappers.getMapper(PaymentInitiationStatusMapper.class);

    @Test
    public void toPaymentInitiationStatusResponse200Json() {
        PaymentInitiationStatus paymentInitiationStatus = new PaymentInitiationStatus();
        paymentInitiationStatus.setTransactionStatus(TransactionStatus.RCVD);
        PaymentInitiationStatusResponse200Json paymentInitiationStatusResponse200Json =
                paymentInitiationStatusMapper.toPaymentInitiationStatusResponse200Json(paymentInitiationStatus);
        assertThat(paymentInitiationStatusResponse200Json.getTransactionStatus()).isEqualTo(de.adorsys.xs2a.gateway.model.pis.TransactionStatus.RCVD);
    }
}