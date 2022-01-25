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

package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ConsentsResponse201 {
    private ConsentStatus consentStatus;

    private String consentId;

    private List<AuthenticationObject> scaMethods;

    private Object chosenScaMethod;

    private ChallengeData challengeData;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private String psuMessage;

    private List<TppMessageGeneric> tppMessages;

    private ScaStatus scaStatus;

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public List<AuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<AuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public Object getChosenScaMethod() {
        return chosenScaMethod;
    }

    public void setChosenScaMethod(Object chosenScaMethod) {
        this.chosenScaMethod = chosenScaMethod;
    }

    public ChallengeData getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(ChallengeData challengeData) {
        this.challengeData = challengeData;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    public List<TppMessageGeneric> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessageGeneric> tppMessages) {
        this.tppMessages = tppMessages;
    }

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsentsResponse201 that = (ConsentsResponse201) o;
        return Objects.equals(consentStatus, that.consentStatus) &&
            Objects.equals(consentId, that.consentId) &&
            Objects.equals(scaMethods, that.scaMethods) &&
            Objects.equals(chosenScaMethod, that.chosenScaMethod) &&
            Objects.equals(challengeData, that.challengeData) &&
            Objects.equals(links, that.links) &&
            Objects.equals(psuMessage, that.psuMessage) &&
            Objects.equals(tppMessages, that.tppMessages) &&
            Objects.equals(scaStatus, that.scaStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consentStatus,
            consentId,
            scaMethods,
            chosenScaMethod,
            challengeData,
            links,
            psuMessage,
            tppMessages,
            scaStatus);
    }
}
