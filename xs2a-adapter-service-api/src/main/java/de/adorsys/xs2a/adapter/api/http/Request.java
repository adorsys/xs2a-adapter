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

package de.adorsys.xs2a.adapter.api.http;

import de.adorsys.xs2a.adapter.api.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Request {

    interface Builder {

        String method();

        String uri();

        String body();

        Builder jsonBody(String body);

        boolean jsonBody();

        Builder emptyBody(boolean empty);

        boolean emptyBody();

        Builder urlEncodedBody(Map<String, String> formData);

        Map<String, String> urlEncodedBody();

        Builder xmlBody(String body);

        boolean xmlBody();

        Builder addXmlPart(String name, String xmlPart);

        Map<String, String> xmlParts();

        Builder addJsonPart(String name, String jsonPart);

        Map<String, String> jsonParts();

        Builder addPlainTextPart(String name, Object part);

        Map<String, String> plainTextParts();

        boolean multipartBody();

        Builder headers(Map<String, String> headers);

        Map<String, String> headers();

        Builder header(String name, String value);

        <T> Response<T> send(HttpClient.ResponseHandler<T> responseHandler, List<Interceptor> interceptors);

        default <T> Response<T> send(HttpClient.ResponseHandler<T> responseHandler) {
            return send(responseHandler, Collections.emptyList());
        }

        String content();
    }
}
