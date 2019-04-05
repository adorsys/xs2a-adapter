package de.adorsys.xs2a.gateway.service.impl.mapper;

import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankPaymentInitiationResponse;
import de.adorsys.xs2a.gateway.service.impl.model.ObjectLink;
import de.adorsys.xs2a.gateway.service.impl.model.ObjectLinks;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentMapperTest {
    private static final String PAYMENT_ID = "payment-id";
    private static final String SCA_REDIRECT_LINK = "sca redirect link";
    private static final Amount TRANSACTION_FEES = new Amount();
    private static final AuthenticationObject[] SCA_METHODS = new AuthenticationObject[0];
    private static final ChallengeData CHALLENGE_DATA = new ChallengeData();
    private static final String PSU_MESSAGE = "psu message";
    private static final MessageErrorCode[] TPP_MESSAGES = new MessageErrorCode[0];

    private PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    public void toPaymentInitiationRequestResponse() {
        PaymentInitiationRequestResponse paymentInitiationRequestResponse =
                paymentMapper.toPaymentInitiationRequestResponse(deutscheBankPaymentInitiationResponse());
        assertThat(paymentInitiationRequestResponse.getPaymentId()).isEqualTo(PAYMENT_ID);
        assertThat(paymentInitiationRequestResponse.getTransactionStatus()).isEqualTo(TransactionStatus.RCVD);
        assertThat(paymentInitiationRequestResponse.getLinks().getScaRedirect()).isEqualTo(SCA_REDIRECT_LINK);
        assertThat(paymentInitiationRequestResponse.getScaStatus()).isEqualTo(ScaStatus.RECEIVED);
        assertThat(paymentInitiationRequestResponse.getTransactionFees()).isEqualTo(TRANSACTION_FEES);
        assertThat(paymentInitiationRequestResponse.isTransactionFeeIndicator()).isTrue();
        assertThat(paymentInitiationRequestResponse.isMultilevelScaRequired()).isTrue();
        assertThat(paymentInitiationRequestResponse.getScaMethods()).isEqualTo(SCA_METHODS);
        assertThat(paymentInitiationRequestResponse.getChallengeData()).isEqualTo(CHALLENGE_DATA);
        assertThat(paymentInitiationRequestResponse.getPsuMessage()).isEqualTo(PSU_MESSAGE);
        assertThat(paymentInitiationRequestResponse.getTppMessages()).isEqualTo(TPP_MESSAGES);
    }

    private DeutscheBankPaymentInitiationResponse deutscheBankPaymentInitiationResponse() {
        DeutscheBankPaymentInitiationResponse deutscheBankPaymentInitiationResponse = new DeutscheBankPaymentInitiationResponse();
        deutscheBankPaymentInitiationResponse.setTransactionStatus(TransactionStatus.RCVD);
        deutscheBankPaymentInitiationResponse.setPaymentId(PAYMENT_ID);
        deutscheBankPaymentInitiationResponse.setLinks(links());
        deutscheBankPaymentInitiationResponse.setScaStatus(ScaStatus.RECEIVED);
        deutscheBankPaymentInitiationResponse.setTransactionFees(TRANSACTION_FEES);
        deutscheBankPaymentInitiationResponse.setTransactionFeeIndicator(true);
        deutscheBankPaymentInitiationResponse.setMultilevelScaRequired(true);
        deutscheBankPaymentInitiationResponse.setScaMethods(SCA_METHODS);
        deutscheBankPaymentInitiationResponse.setChallengeData(CHALLENGE_DATA);
        deutscheBankPaymentInitiationResponse.setPsuMessage(PSU_MESSAGE);
        deutscheBankPaymentInitiationResponse.setTppMessages(TPP_MESSAGES);
        return deutscheBankPaymentInitiationResponse;
    }

    private ObjectLinks links() {
        ObjectLinks objectLinks = new ObjectLinks();
        ObjectLink scaRedirect = new ObjectLink();
        scaRedirect.setHref(SCA_REDIRECT_LINK);
        objectLinks.setScaRedirect(scaRedirect);
        return objectLinks;
    }
}