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

package de.adorsys.xs2a.adapter;

import de.adorsys.xs2a.adapter.api.model.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestModelBuilder {

    private TestModelBuilder() {
    }

    public static final String MESSAGE = "message";
    public static final String CONSTENT_ID = "constent-ID";
    public static final String NAME = "SMS OTP on phone +49160 xxxxx 28";
    public static final AuthenticationType TYPE = AuthenticationType.SMS_OTP;
    public static final String EXPLANATION = "some explanation";
    public static final String VERSION = "v1.2";
    public static final String METHOD_ID = "authMethodId3";
    public static final String ADDITIONAL_INFO = "additionalInfo";
    public static final List<String> DATA = Collections.singletonList("data");
    public static final String IMAGE = "image";
    public static final String LINK = "http://link-to-image";
    public static final int LENGTH = 123;

    public static ConsentsResponse201 buildConsentCreationResponse() {
        ConsentsResponse201 response = new ConsentsResponse201();
        response.setPsuMessage(MESSAGE);
        response.setConsentId(CONSTENT_ID);
        response.setConsentStatus(ConsentStatus.RECEIVED);
        Map<String, HrefType> links = new HashMap<>();
        HrefType link = new HrefType();
        link.setHref(MESSAGE);
        links.put(CONSTENT_ID, link);
        response.setLinks(links);
        response.setScaMethods(Collections.singletonList(buildAuthenticationObject()));
        response.setChosenScaMethod(buildAuthenticationObject());
        response.setChallengeData(buildChallengeData());
        return response;
    }

    public static AuthenticationObject buildAuthenticationObject() {
        AuthenticationObject authenticationObject = new AuthenticationObject();
        authenticationObject.setName(NAME);
        authenticationObject.setAuthenticationType(TYPE);
        authenticationObject.setExplanation(EXPLANATION);
        authenticationObject.setAuthenticationVersion(VERSION);
        authenticationObject.setAuthenticationMethodId(METHOD_ID);
        return authenticationObject;
    }

    public static ChallengeData buildChallengeData() {
        ChallengeData data = new ChallengeData();
        data.setAdditionalInformation(ADDITIONAL_INFO);
        data.setData(DATA);
        data.setImageLink(LINK);
        data.setOtpFormat(ChallengeData.OtpFormat.CHARACTERS);
        data.setOtpMaxLength(LENGTH);
        data.setImage(IMAGE.getBytes());
        return data;
    }

}
