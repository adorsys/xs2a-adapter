package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.PaymentInitiationStatusResponse200JsonTO;
import de.adorsys.xs2a.adapter.model.TransactionStatusTO;
import de.adorsys.xs2a.adapter.service.model.PaymentInitiationStatus;
import de.adorsys.xs2a.adapter.service.model.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentInitiationStatusMapperTest {

    private PaymentInitiationStatusMapper paymentInitiationStatusMapper = Mappers.getMapper(PaymentInitiationStatusMapper.class);

    @Test
    public void toPaymentInitiationStatusResponse200Json() {
        PaymentInitiationStatus paymentInitiationStatus = new PaymentInitiationStatus();
        paymentInitiationStatus.setTransactionStatus(TransactionStatus.RCVD);
        PaymentInitiationStatusResponse200JsonTO paymentInitiationStatusResponse200Json =
                paymentInitiationStatusMapper.toPaymentInitiationStatusResponse200Json(paymentInitiationStatus);
        assertThat(paymentInitiationStatusResponse200Json.getTransactionStatus()).isEqualTo(TransactionStatusTO.RCVD);
    }
}
