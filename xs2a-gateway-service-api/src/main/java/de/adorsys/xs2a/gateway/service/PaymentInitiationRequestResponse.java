package de.adorsys.xs2a.gateway.service;

public class PaymentInitiationRequestResponse {
    // required
    private TransactionStatus transactionStatus;
    private String paymentId;
    private Links links;
    // optional
    private ScaStatus scaStatus;
    private Amount transactionFees;
    private boolean transactionFeeIndicator;
    private boolean multilevelScaRequired;
    private AuthenticationObject[] scaMethods;
    private ChallengeData challengeData;
    private String psuMessage;
    private MessageErrorCode[] tppMessages;
}
