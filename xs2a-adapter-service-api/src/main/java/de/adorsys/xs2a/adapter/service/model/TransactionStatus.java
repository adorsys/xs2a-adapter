/*
 * Copyright 2018-2019 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.service.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TransactionStatus {

    // Settlement on the creditor's account has been completed.
    ACCC("AcceptedSettlementCompletedCreditor", true),
    // Preceding check of technical validation was successful.
    // Customer profile check was also successful
    ACCP("AcceptedCustomerProfile", false),
    // Settlement on the debtor's account has been completed. This can be used by
    // the first agent to report to the debtor that the transaction has been completed.
    // This status is provided for transaction status reasons, not for financial information.
    // It can only be used after bilateral agreement
    ACSC("AcceptedSettlementCompleted", true),
    // All preceding checks such as technical validation and customer profile were
    // successful and therefore the payment initiation has been accepted for execution
    ACSP("AcceptedSettlementInProcess", false),
    // AuthenticationObject and syntactical and semantical validation are successful
    ACTC("AcceptedTechnicalValidation", false),
    // Instruction is accepted but a change will be made, such as date or remittance not sent
    ACWC("AcceptedWithChange", false),
    // Payment instruction included in the credit transfer is accepted without
    // being posted to the creditor customer’s account
    ACWP("AcceptedWithoutPosting", false),
    // Payment initiation has been received by the receiving agent
    RCVD("Received", false),
    // Payment initiation or individual transaction included in the payment
    // initiation is pending. Further checks and status update will be performed
    PDNG("Pending", false),
    // Payment initiation or individual transaction included in the payment initiation
    // has been rejected
    RJCT("Rejected", true),
    // Canceled
    CANC("Canceled", true),
    // Preceeding check of technical validation and customer profile was successful
    // and an automatic funds check was positive
    ACFC("AcceptedFundsChecked", false),
    // The payment initiation needs multiple authentications, where some
    // but not yet all have been performed. Syntactical and semantical validations
    // are successful.
    PATC("PartiallyAcceptedTechnicalCorrect", false),
    PART("PART", false);


    private static Map<String, TransactionStatus> container = new HashMap<>();

    static {
        Arrays.stream(values())
            .forEach(status -> container.put(status.getTransactionStatus(), status));
    }

    private String transactionStatus;
    private final boolean finalisedStatus;

    public boolean isFinalisedStatus() {
        return finalisedStatus;
    }

    public boolean isNotFinalisedStatus() {
        return !isFinalisedStatus();
    }

    TransactionStatus(String transactionStatus, boolean finalisedStatus) {
        this.transactionStatus = transactionStatus;
        this.finalisedStatus = finalisedStatus;
    }

    public static TransactionStatus getByValue(String transactionStatus) {
        return container.get(transactionStatus);
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }
}
