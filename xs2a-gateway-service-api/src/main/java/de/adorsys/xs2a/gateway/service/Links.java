/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
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

package de.adorsys.xs2a.gateway.service;

public class Links {
    private String scaRedirect;
    private String scaOAuth;
    private String updatePsuIdentification;
    private String updateProprietaryData;
    private String updatePsuAuthentication;
    private String selectAuthenticationMethod;
    private String self;
    private String status;
    private String account;
    private String viewBalances;
    private String viewTransactions;
    private String first;
    private String next;
    private String previous;
    private String last;
    private String download;
    private String startAuthorisation;
    private String startAuthorisationWithPsuIdentification;
    private String startAuthorisationWithPsuAuthentication;
    private String startAuthorisationWithAuthenticationMethodSelection;
    private String startAuthorisationWithTransactionAuthorisation;
    private String scaStatus;
    private String authoriseTransaction;

    public String getScaRedirect() {
        return scaRedirect;
    }

    public void setScaRedirect(String scaRedirect) {
        this.scaRedirect = scaRedirect;
    }

    public String getScaOAuth() {
        return scaOAuth;
    }

    public void setScaOAuth(String scaOAuth) {
        this.scaOAuth = scaOAuth;
    }

    public String getUpdatePsuIdentification() {
        return updatePsuIdentification;
    }

    public void setUpdatePsuIdentification(String updatePsuIdentification) {
        this.updatePsuIdentification = updatePsuIdentification;
    }

    public String getUpdateProprietaryData() {
        return updateProprietaryData;
    }

    public void setUpdateProprietaryData(String updateProprietaryData) {
        this.updateProprietaryData = updateProprietaryData;
    }

    public String getUpdatePsuAuthentication() {
        return updatePsuAuthentication;
    }

    public void setUpdatePsuAuthentication(String updatePsuAuthentication) {
        this.updatePsuAuthentication = updatePsuAuthentication;
    }

    public String getSelectAuthenticationMethod() {
        return selectAuthenticationMethod;
    }

    public void setSelectAuthenticationMethod(String selectAuthenticationMethod) {
        this.selectAuthenticationMethod = selectAuthenticationMethod;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getViewBalances() {
        return viewBalances;
    }

    public void setViewBalances(String viewBalances) {
        this.viewBalances = viewBalances;
    }

    public String getViewTransactions() {
        return viewTransactions;
    }

    public void setViewTransactions(String viewTransactions) {
        this.viewTransactions = viewTransactions;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getStartAuthorisation() {
        return startAuthorisation;
    }

    public void setStartAuthorisation(String startAuthorisation) {
        this.startAuthorisation = startAuthorisation;
    }

    public String getStartAuthorisationWithPsuIdentification() {
        return startAuthorisationWithPsuIdentification;
    }

    public void setStartAuthorisationWithPsuIdentification(String startAuthorisationWithPsuIdentification) {
        this.startAuthorisationWithPsuIdentification = startAuthorisationWithPsuIdentification;
    }

    public String getStartAuthorisationWithPsuAuthentication() {
        return startAuthorisationWithPsuAuthentication;
    }

    public void setStartAuthorisationWithPsuAuthentication(String startAuthorisationWithPsuAuthentication) {
        this.startAuthorisationWithPsuAuthentication = startAuthorisationWithPsuAuthentication;
    }

    public String getStartAuthorisationWithAuthenticationMethodSelection() {
        return startAuthorisationWithAuthenticationMethodSelection;
    }

    public void setStartAuthorisationWithAuthenticationMethodSelection(String startAuthorisationWithAuthenticationMethodSelection) {
        this.startAuthorisationWithAuthenticationMethodSelection = startAuthorisationWithAuthenticationMethodSelection;
    }

    public String getStartAuthorisationWithTransactionAuthorisation() {
        return startAuthorisationWithTransactionAuthorisation;
    }

    public void setStartAuthorisationWithTransactionAuthorisation(String startAuthorisationWithTransactionAuthorisation) {
        this.startAuthorisationWithTransactionAuthorisation = startAuthorisationWithTransactionAuthorisation;
    }

    public String getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(String scaStatus) {
        this.scaStatus = scaStatus;
    }

    public String getAuthoriseTransaction() {
        return authoriseTransaction;
    }

    public void setAuthoriseTransaction(String authoriseTransaction) {
        this.authoriseTransaction = authoriseTransaction;
    }
}
