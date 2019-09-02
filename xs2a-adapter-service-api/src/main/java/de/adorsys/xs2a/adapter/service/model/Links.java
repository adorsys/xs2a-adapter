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

package de.adorsys.xs2a.adapter.service.model;

import de.adorsys.xs2a.adapter.service.model.Link;

public class Links {
    private Link scaRedirect;
    private Link scaOAuth;
    private Link updatePsuIdentification;
    private Link updateProprietaryData;
    private Link updatePsuAuthentication;
    private Link selectAuthenticationMethod;
    private Link self;
    private Link status;
    private Link account;
    private Link viewBalances;
    private Link viewTransactions;
    private Link first;
    private Link next;
    private Link previous;
    private Link last;
    private Link download;
    private Link startAuthorisation;
    private Link startAuthorisationWithPsuIdentification;
    private Link startAuthorisationWithPsuAuthentication;
    private Link startAuthorisationWithAuthenticationMethodSelection;
    private Link startAuthorisationWithTransactionAuthorisation;
    private Link scaStatus;
    private Link authoriseTransaction;

    public Link getScaRedirect() {
        return scaRedirect;
    }

    public void setScaRedirect(Link scaRedirect) {
        this.scaRedirect = scaRedirect;
    }

    public Link getScaOAuth() {
        return scaOAuth;
    }

    public void setScaOAuth(Link scaOAuth) {
        this.scaOAuth = scaOAuth;
    }

    public Link getUpdatePsuIdentification() {
        return updatePsuIdentification;
    }

    public void setUpdatePsuIdentification(Link updatePsuIdentification) {
        this.updatePsuIdentification = updatePsuIdentification;
    }

    public Link getUpdateProprietaryData() {
        return updateProprietaryData;
    }

    public void setUpdateProprietaryData(Link updateProprietaryData) {
        this.updateProprietaryData = updateProprietaryData;
    }

    public Link getUpdatePsuAuthentication() {
        return updatePsuAuthentication;
    }

    public void setUpdatePsuAuthentication(Link updatePsuAuthentication) {
        this.updatePsuAuthentication = updatePsuAuthentication;
    }

    public Link getSelectAuthenticationMethod() {
        return selectAuthenticationMethod;
    }

    public void setSelectAuthenticationMethod(Link selectAuthenticationMethod) {
        this.selectAuthenticationMethod = selectAuthenticationMethod;
    }

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    public Link getStatus() {
        return status;
    }

    public void setStatus(Link status) {
        this.status = status;
    }

    public Link getAccount() {
        return account;
    }

    public void setAccount(Link account) {
        this.account = account;
    }

    public Link getViewBalances() {
        return viewBalances;
    }

    public void setViewBalances(Link viewBalances) {
        this.viewBalances = viewBalances;
    }

    public Link getViewTransactions() {
        return viewTransactions;
    }

    public void setViewTransactions(Link viewTransactions) {
        this.viewTransactions = viewTransactions;
    }

    public Link getFirst() {
        return first;
    }

    public void setFirst(Link first) {
        this.first = first;
    }

    public Link getNext() {
        return next;
    }

    public void setNext(Link next) {
        this.next = next;
    }

    public Link getPrevious() {
        return previous;
    }

    public void setPrevious(Link previous) {
        this.previous = previous;
    }

    public Link getLast() {
        return last;
    }

    public void setLast(Link last) {
        this.last = last;
    }

    public Link getDownload() {
        return download;
    }

    public void setDownload(Link download) {
        this.download = download;
    }

    public Link getStartAuthorisation() {
        return startAuthorisation;
    }

    public void setStartAuthorisation(Link startAuthorisation) {
        this.startAuthorisation = startAuthorisation;
    }

    public Link getStartAuthorisationWithPsuIdentification() {
        return startAuthorisationWithPsuIdentification;
    }

    public void setStartAuthorisationWithPsuIdentification(Link startAuthorisationWithPsuIdentification) {
        this.startAuthorisationWithPsuIdentification = startAuthorisationWithPsuIdentification;
    }

    public Link getStartAuthorisationWithPsuAuthentication() {
        return startAuthorisationWithPsuAuthentication;
    }

    public void setStartAuthorisationWithPsuAuthentication(Link startAuthorisationWithPsuAuthentication) {
        this.startAuthorisationWithPsuAuthentication = startAuthorisationWithPsuAuthentication;
    }

    public Link getStartAuthorisationWithAuthenticationMethodSelection() {
        return startAuthorisationWithAuthenticationMethodSelection;
    }

    public void setStartAuthorisationWithAuthenticationMethodSelection(Link startAuthorisationWithAuthenticationMethodSelection) {
        this.startAuthorisationWithAuthenticationMethodSelection = startAuthorisationWithAuthenticationMethodSelection;
    }

    public Link getStartAuthorisationWithTransactionAuthorisation() {
        return startAuthorisationWithTransactionAuthorisation;
    }

    public void setStartAuthorisationWithTransactionAuthorisation(Link startAuthorisationWithTransactionAuthorisation) {
        this.startAuthorisationWithTransactionAuthorisation = startAuthorisationWithTransactionAuthorisation;
    }

    public Link getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(Link scaStatus) {
        this.scaStatus = scaStatus;
    }

    public Link getAuthoriseTransaction() {
        return authoriseTransaction;
    }

    public void setAuthoriseTransaction(Link authoriseTransaction) {
        this.authoriseTransaction = authoriseTransaction;
    }
}
