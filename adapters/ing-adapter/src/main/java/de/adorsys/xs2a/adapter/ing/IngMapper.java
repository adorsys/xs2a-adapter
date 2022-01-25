/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.ing.model.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper
public interface IngMapper {
    AccountList map(IngAccountsResponse value);

    ReadAccountBalanceResponse200 map(IngBalancesResponse value);

    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "links", ignore = true)
    TransactionsResponse200Json map(IngTransactionsResponse value);

    default String map(UUID value) {
        return value.toString();
    }

    default Map<String, HrefType> map(IngAccountLinks value) {
        if (value == null) {
            return null;
        }
        HashMap<String, HrefType> links = new HashMap<>();
        if (value.getBalances() != null) {
            HrefType balances = new HrefType();
            balances.setHref(value.getBalances().getHref());
            links.put("balances", balances);
        }
        if (value.getTransactions() != null) {
            HrefType transactions = new HrefType();
            transactions.setHref(value.getTransactions().getHref());
            links.put("transactions", transactions);
        }
        return links;
    }

    default Map<String, HrefType> map(IngLinksNext value) {
        if (value == null) {
            return null;
        }
        if (value.getNext() != null) {
            HrefType next = new HrefType();
            next.setHref(value.getNext().getHref());
            return Collections.singletonMap("next", next);
        }
        return Collections.emptyMap();
    }

    @Mapping(target = "bban", ignore = true)
    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    AccountReference map(IngAccountReferenceIban value);

    @Mapping(target = "bban", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    @Mapping(target = "cashAccountType", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "bic", ignore = true)
    @Mapping(target = "linkedAccounts", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    @Mapping(target = "ownerName", ignore = true)
    @Mapping(target = "usage", ignore = true)
    AccountDetails map(IngAccount value);

    @Mapping(target = "creditLimitIncluded", ignore = true)
    @Mapping(target = "lastCommittedTransaction", ignore = true)
    Balance map(IngBalance value);

    default BalanceType toBalanceType(String value) {
        return BalanceType.fromValue(value);
    }

    default TransactionStatus toTransactionStatus(String value) {
        if ("ACTV".equals(value)) {
            return TransactionStatus.ACSP;
        }
        if ("EXPI".equals(value)) {
            return TransactionStatus.ACSC;
        }
        return TransactionStatus.fromValue(value);
    }

    default TppMessageCategory toTppMessageCategory(String value) {
        return TppMessageCategory.fromValue(value);
    }

    default MessageCode2XX toMessageCode2XX(String value) {
        return MessageCode2XX.fromValue(value);
    }

    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    @Mapping(target = "currency", ignore = true)
    AccountReference map(IngCounterpartyAccount value);

    @Mapping(target = "entryReference", ignore = true)
    @Mapping(target = "mandateId", ignore = true)
    @Mapping(target = "checkId", ignore = true)
    @Mapping(target = "creditorId", ignore = true)
    @Mapping(target = "ultimateCreditor", ignore = true)
    @Mapping(target = "ultimateDebtor", ignore = true)
    @Mapping(target = "purposeCode", ignore = true)
    @Mapping(target = "bankTransactionCode", ignore = true)
    @Mapping(target = "proprietaryBankTransactionCode", ignore = true)
    @Mapping(target = "links", ignore = true)
    @Mapping(target = "creditorAgent", ignore = true)
    @Mapping(target = "debtorAgent", ignore = true)
    @Mapping(target = "remittanceInformationUnstructuredArray", ignore = true)
    @Mapping(target = "remittanceInformationStructuredArray", ignore = true)
    @Mapping(target = "additionalInformationStructured", ignore = true)
    @Mapping(target = "balanceAfterTransaction", ignore = true)
    @Mapping(target = "currencyExchange", ignore = true)
    @Mapping(target = "additionalInformation", ignore = true)
    Transactions map(IngTransaction value);

    default String map(IngTransactionRemittanceInformationStructured value) {
        return value == null ? null : value.getReference();
    }

    TokenResponse map(IngTokenResponse value);

    @Mapping(target = "chargeBearer", ignore = true)
    @Mapping(target = "clearingSystemMemberIdentification", ignore = true)
    @Mapping(target = "debtorName", ignore = true)
    @Mapping(target = "debtorAgent", ignore = true)
    @Mapping(target = "instructionPriority", ignore = true)
    @Mapping(target = "serviceLevelCode", ignore = true)
    @Mapping(target = "localInstrumentCode", ignore = true)
    @Mapping(target = "categoryPurposeCode", ignore = true)
    @Mapping(target = "requestedExecutionDate", ignore = true)
    IngPaymentInstruction map(PaymentInitiationJson value);

    @Mapping(target = "streetName", source = "street")
    @Mapping(target = "townName", source = "city")
    @Mapping(target = "postCode", source = "postalCode")
    Address map(IngAddress value);

    @InheritInverseConfiguration
    IngAddress map(Address value);

    @Mapping(target = "transactionFees", ignore = true)
    @Mapping(target = "transactionFeeIndicator", ignore = true)
    @Mapping(target = "scaMethods", ignore = true)
    @Mapping(target = "challengeData", ignore = true)
    @Mapping(target = "psuMessage", ignore = true)
    @Mapping(target = "chosenScaMethod", ignore = true)
    PaymentInitationRequestResponse201 map(IngPaymentInitiationResponse value);

    default Map<String, HrefType> map(IngLinks value) {
        if (value == null) {
            return null;
        }
        HashMap<String, HrefType> links = new HashMap<>();
        if (value.getScaRedirect() != null) {
            HrefType scaRedirect = new HrefType();
            scaRedirect.setHref(value.getScaRedirect());
            links.put("scaRedirect", scaRedirect);
        }
        if (value.getSelf() != null) {
            HrefType self = new HrefType();
            self.setHref(value.getSelf());
            links.put("self", self);
        }
        if (value.getStatus() != null) {
            HrefType status = new HrefType();
            status.setHref(value.getStatus());
            links.put("status", status);
        }
        if (value.getDelete() != null) {
            HrefType delete = new HrefType();
            delete.setHref(value.getDelete());
            links.put("delete", delete);
        }
        return links;
    }

    @Mapping(target = "transactionStatus", ignore = true)
    PaymentInitiationWithStatusResponse map(IngPaymentInstruction value);

    @Mapping(target = "bban", ignore = true)
    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    AccountReference map(IngDebtorAccount value);

    @Mapping(target = "pan", ignore = true)
    @Mapping(target = "maskedPan", ignore = true)
    @Mapping(target = "msisdn", ignore = true)
    AccountReference map(IngCreditorAccount value);

    @Mapping(target = "fundsAvailable", ignore = true)
    @Mapping(target = "psuMessage", ignore = true)
    PaymentInitiationStatusResponse200Json map(IngPaymentStatusResponse value);

    @Mapping(target = "chargeBearer", ignore = true)
    @Mapping(target = "clearingSystemMemberIdentification", ignore = true)
    @Mapping(target = "debtorName", ignore = true)
    @Mapping(target = "debtorAgent", ignore = true)
    @Mapping(target = "instructionPriority", ignore = true)
    @Mapping(target = "serviceLevelCode", ignore = true)
    @Mapping(target = "localInstrumentCode", ignore = true)
    @Mapping(target = "categoryPurposeCode", ignore = true)
    @Mapping(target = "requestedExecutionDate", ignore = true)
    IngPeriodicPaymentInitiationJson map(PeriodicPaymentInitiationJson value);

    default IngFrequencyCode map(FrequencyCode value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case DAILY:
                return IngFrequencyCode.DAIL;
            case WEEKLY:
                return IngFrequencyCode.WEEK;
            case EVERYTWOWEEKS:
                return IngFrequencyCode.TOWK;
            case MONTHLY:
                return IngFrequencyCode.MNTH;
            case EVERYTWOMONTHS:
                return IngFrequencyCode.TOMN;
            case QUARTERLY:
                return IngFrequencyCode.QUTR;
            case SEMIANNUAL:
                return IngFrequencyCode.SEMI;
            case ANNUAL:
                return IngFrequencyCode.YEAR;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Mapping(target = "transactionFees", ignore = true)
    @Mapping(target = "transactionFeeIndicator", ignore = true)
    @Mapping(target = "scaMethods", ignore = true)
    @Mapping(target = "chosenScaMethod", ignore = true)
    @Mapping(target = "challengeData", ignore = true)
    @Mapping(target = "psuMessage", ignore = true)
    PaymentInitationRequestResponse201 map(IngPeriodicPaymentInitiationResponse value);


    default Map<String, HrefType> map(IngPeriodicLinks value) {
        if (value == null) {
            return null;
        }
        HashMap<String, HrefType> links = new HashMap<>();
        if (value.getScaRedirect() != null) {
            HrefType scaRedirect = new HrefType();
            scaRedirect.setHref(value.getScaRedirect());
            links.put("scaRedirect", scaRedirect);
        }
        if (value.getSelf() != null) {
            HrefType self = new HrefType();
            self.setHref(value.getSelf());
            links.put("self", self);
        }
        if (value.getStatus() != null) {
            HrefType status = new HrefType();
            status.setHref(value.getStatus());
            links.put("status", status);
        }
        if (value.getDelete() != null) {
            HrefType delete = new HrefType();
            delete.setHref(value.getDelete());
            links.put("delete", delete);
        }
        return links;
    }

    @Mapping(target = "executionRule", ignore = true)
    @Mapping(target = "transactionStatus", ignore = true)
    PeriodicPaymentInitiationWithStatusResponse map(IngPeriodicPaymentInitiationJson value);

    default FrequencyCode map(IngFrequencyCode value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case DAIL:
                return FrequencyCode.DAILY;
            case WEEK:
                return FrequencyCode.WEEKLY;
            case TOWK:
                return FrequencyCode.EVERYTWOWEEKS;
            case MNTH:
                return FrequencyCode.MONTHLY;
            case TOMN:
                return FrequencyCode.EVERYTWOMONTHS;
            case QUTR:
                return FrequencyCode.QUARTERLY;
            case SEMI:
                return FrequencyCode.SEMIANNUAL;
            case YEAR:
                return FrequencyCode.ANNUAL;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Mapping(target = "fundsAvailable", ignore = true)
    @Mapping(target = "psuMessage", ignore = true)
    PaymentInitiationStatusResponse200Json map(IngPaymentAgreementStatusResponse value);

    default IngPeriodicPaymentInitiationXml map(PeriodicPaymentInitiationMultipartBody value) {
        if (value == null) {
            return null;
        }
        IngPeriodicPaymentInitiationXml target = new IngPeriodicPaymentInitiationXml();
        target.setXml_sct(value.getXml_sct() == null ? null : value.getXml_sct().toString());
        if (value.getJson_standingorderType() != null) {
            target.setStartDate(value.getJson_standingorderType().getStartDate());
            target.setEndDate(value.getJson_standingorderType().getEndDate());
            target.setFrequency(map(value.getJson_standingorderType().getFrequency()));
            target.setDayOfExecution(map(value.getJson_standingorderType().getDayOfExecution()));
        }
        return target;
    }

    IngDayOfExecution map(DayOfExecution value);

    DayOfExecution map(IngDayOfExecution value);

    default PeriodicPaymentInitiationMultipartBody map(IngPeriodicPaymentInitiationXml value) {
        if (value == null) {
            return null;
        }
        PeriodicPaymentInitiationMultipartBody target = new PeriodicPaymentInitiationMultipartBody();
        target.setXml_sct(value.getXml_sct());
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json =
            new PeriodicPaymentInitiationXmlPart2StandingorderTypeJson();
        target.setJson_standingorderType(json);
        json.setStartDate(value.getStartDate());
        json.setEndDate(value.getEndDate());
        json.setFrequency(map(value.getFrequency()));
        json.setDayOfExecution(map(value.getDayOfExecution()));
        return target;
    }
}
