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

package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.OauthState;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class OAuthConsentResponseWrapper {

    private static final String STATE_REGEX = "[&?]state=([^&]*)";
    private static final Pattern STATE_PATTERN = Pattern.compile(STATE_REGEX);
    static final String SCA_OAUTH = "scaOAuth";
    static final String STATE_PARAMETER = "&state=";

    private OAuthConsentResponseWrapper() {
    }

    public static Response<ConsentsResponse> wrap(Map<String, String> headers, Response<ConsentsResponse> response) {
        Map<String, HrefType> links = response.getBody().getLinks();
        if (links != null && links.containsKey(SCA_OAUTH)) {
            RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

            String aspspId = requestHeaders
                                 .getAspspId()
                                 .filter(s -> !s.isEmpty())
                                 .orElseThrow(() -> new IllegalStateException(RequestHeaders.X_GTW_ASPSP_ID + " header must be present in the request"));

            String psuId = requestHeaders
                               .get(RequestHeaders.PSU_ID)
                               .filter(s -> !s.isEmpty())
                               .orElse(UUID.randomUUID().toString());


            OauthState state = new OauthState(psuId, aspspId);

            String scaOauthLink = links.get(SCA_OAUTH).getHref();

            String encodedState = encodeState(state);

            Matcher matcher = STATE_PATTERN.matcher(scaOauthLink);

            if (matcher.find()) {
                StringBuffer sb = new StringBuffer();
                String quote = Pattern.quote(matcher.group(1));
                matcher.appendReplacement(sb, matcher.group(0).replaceFirst(quote, encodedState));
                matcher.appendTail(sb);
                scaOauthLink = sb.toString();
            } else {
                scaOauthLink += STATE_PARAMETER + encodedState;
            }

            links.put(SCA_OAUTH, new HrefType(scaOauthLink));
        }
        return response;
    }

    static String encodeState(OauthState state) {
        return Base64.getEncoder().encodeToString(state.toJson().getBytes());
    }
}
