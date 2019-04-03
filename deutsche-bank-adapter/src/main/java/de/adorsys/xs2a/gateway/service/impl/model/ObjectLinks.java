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

package de.adorsys.xs2a.gateway.service.impl.model;

public class ObjectLinks {
    private ObjectLink scaRedirect;
    private ObjectLink scaOAuth;
    private ObjectLink updatePsuIdentification;
    private ObjectLink updateProprietaryData;
    private ObjectLink updatePsuAuthentication;
    private ObjectLink selectAuthenticationMethod;
    private ObjectLink self;
    private ObjectLink status;
    private ObjectLink account;
    private ObjectLink viewBalances;
    private ObjectLink viewTransactions;
    private ObjectLink first;
    private ObjectLink next;
    private ObjectLink previous;
    private ObjectLink last;
    private ObjectLink download;
    private ObjectLink startAuthorisation;
    private ObjectLink startAuthorisationWithPsuIdentification;
    private ObjectLink startAuthorisationWithPsuAuthentication;
    private ObjectLink startAuthorisationWithAuthenticationMethodSelection;
    private ObjectLink startAuthorisationWithTransactionAuthorisation;
    private ObjectLink scaStatus;
    private ObjectLink authoriseTransaction;

    public ObjectLink getScaRedirect() {
        return scaRedirect;
    }

    public void setScaRedirect(ObjectLink scaRedirect) {
        this.scaRedirect = scaRedirect;
    }

    public ObjectLink getScaOAuth() {
        return scaOAuth;
    }

    public void setScaOAuth(ObjectLink scaOAuth) {
        this.scaOAuth = scaOAuth;
    }

    public ObjectLink getUpdatePsuIdentification() {
        return updatePsuIdentification;
    }

    public void setUpdatePsuIdentification(ObjectLink updatePsuIdentification) {
        this.updatePsuIdentification = updatePsuIdentification;
    }

    public ObjectLink getUpdateProprietaryData() {
        return updateProprietaryData;
    }

    public void setUpdateProprietaryData(ObjectLink updateProprietaryData) {
        this.updateProprietaryData = updateProprietaryData;
    }

    public ObjectLink getUpdatePsuAuthentication() {
        return updatePsuAuthentication;
    }

    public void setUpdatePsuAuthentication(ObjectLink updatePsuAuthentication) {
        this.updatePsuAuthentication = updatePsuAuthentication;
    }

    public ObjectLink getSelectAuthenticationMethod() {
        return selectAuthenticationMethod;
    }

    public void setSelectAuthenticationMethod(ObjectLink selectAuthenticationMethod) {
        this.selectAuthenticationMethod = selectAuthenticationMethod;
    }

    public ObjectLink getSelf() {
        return self;
    }

    public void setSelf(ObjectLink self) {
        this.self = self;
    }

    public ObjectLink getStatus() {
        return status;
    }

    public void setStatus(ObjectLink status) {
        this.status = status;
    }

    public ObjectLink getAccount() {
        return account;
    }

    public void setAccount(ObjectLink account) {
        this.account = account;
    }

    public ObjectLink getViewBalances() {
        return viewBalances;
    }

    public void setViewBalances(ObjectLink viewBalances) {
        this.viewBalances = viewBalances;
    }

    public ObjectLink getViewTransactions() {
        return viewTransactions;
    }

    public void setViewTransactions(ObjectLink viewTransactions) {
        this.viewTransactions = viewTransactions;
    }

    public ObjectLink getFirst() {
        return first;
    }

    public void setFirst(ObjectLink first) {
        this.first = first;
    }

    public ObjectLink getNext() {
        return next;
    }

    public void setNext(ObjectLink next) {
        this.next = next;
    }

    public ObjectLink getPrevious() {
        return previous;
    }

    public void setPrevious(ObjectLink previous) {
        this.previous = previous;
    }

    public ObjectLink getLast() {
        return last;
    }

    public void setLast(ObjectLink last) {
        this.last = last;
    }

    public ObjectLink getDownload() {
        return download;
    }

    public void setDownload(ObjectLink download) {
        this.download = download;
    }

    public ObjectLink getStartAuthorisation() {
        return startAuthorisation;
    }

    public void setStartAuthorisation(ObjectLink startAuthorisation) {
        this.startAuthorisation = startAuthorisation;
    }

    public ObjectLink getStartAuthorisationWithPsuIdentification() {
        return startAuthorisationWithPsuIdentification;
    }

    public void setStartAuthorisationWithPsuIdentification(ObjectLink startAuthorisationWithPsuIdentification) {
        this.startAuthorisationWithPsuIdentification = startAuthorisationWithPsuIdentification;
    }

    public ObjectLink getStartAuthorisationWithPsuAuthentication() {
        return startAuthorisationWithPsuAuthentication;
    }

    public void setStartAuthorisationWithPsuAuthentication(ObjectLink startAuthorisationWithPsuAuthentication) {
        this.startAuthorisationWithPsuAuthentication = startAuthorisationWithPsuAuthentication;
    }

    public ObjectLink getStartAuthorisationWithAuthenticationMethodSelection() {
        return startAuthorisationWithAuthenticationMethodSelection;
    }

    public void setStartAuthorisationWithAuthenticationMethodSelection(ObjectLink startAuthorisationWithAuthenticationMethodSelection) {
        this.startAuthorisationWithAuthenticationMethodSelection = startAuthorisationWithAuthenticationMethodSelection;
    }

    public ObjectLink getStartAuthorisationWithTransactionAuthorisation() {
        return startAuthorisationWithTransactionAuthorisation;
    }

    public void setStartAuthorisationWithTransactionAuthorisation(ObjectLink startAuthorisationWithTransactionAuthorisation) {
        this.startAuthorisationWithTransactionAuthorisation = startAuthorisationWithTransactionAuthorisation;
    }

    public ObjectLink getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ObjectLink scaStatus) {
        this.scaStatus = scaStatus;
    }

    public ObjectLink getAuthoriseTransaction() {
        return authoriseTransaction;
    }

    public void setAuthoriseTransaction(ObjectLink authoriseTransaction) {
        this.authoriseTransaction = authoriseTransaction;
    }
}
