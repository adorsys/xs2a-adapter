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

package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;

import java.util.*;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

public class RequestBuilderImpl implements Request.Builder {
    private final HttpClient httpClient;
    private String method;
    private String uri;
    private Map<String, String> headers = new TreeMap<>(CASE_INSENSITIVE_ORDER);
    private String body;
    private BodyType bodyType;
    private Map<String, String> formData;
    private Map<String, String> xmlParts;
    private Map<String, String> jsonParts;
    private Map<String, String> plainTextParts;

    private enum BodyType {
        JSON, EMPTY_JSON, XML, MULTIPART
    }

    public RequestBuilderImpl(HttpClient httpClient, String method, String uri) {
        this.httpClient = httpClient;
        this.method = method;
        this.uri = uri;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public String uri() {
        return uri;
    }

    public Request.Builder uri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public Request.Builder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Request.Builder header(String name, String value) {
        if (name != null && value != null) {
            headers.put(name, value);
        }
        return this;
    }

    @Override
    public Request.Builder jsonBody(String body) {
        this.body = body;
        bodyType = BodyType.JSON;
        return this;
    }

    @Override
    public boolean jsonBody() {
        return bodyType == BodyType.JSON;
    }

    @Override
    public Request.Builder xmlBody(String body) {
        this.body = body;
        bodyType = BodyType.XML;
        return this;
    }

    @Override
    public boolean xmlBody() {
        return bodyType == BodyType.XML;
    }

    @Override
    public Request.Builder addXmlPart(String name, String xmlPart) {
        if (xmlParts == null) {
            xmlParts = new LinkedHashMap<>();
        }
        xmlParts.put(name, xmlPart);
        bodyType = BodyType.MULTIPART;
        return this;
    }

    @Override
    public Map<String, String> xmlParts() {
        if (xmlParts == null) {
            return Collections.emptyMap();
        }
        return xmlParts;
    }

    @Override
    public Request.Builder addJsonPart(String name, String jsonPart) {
        if (jsonParts == null) {
            jsonParts = new LinkedHashMap<>();
        }
        jsonParts.put(name, jsonPart);
        bodyType = BodyType.MULTIPART;
        return this;
    }

    @Override
    public Map<String, String> jsonParts() {
        if (jsonParts == null) {
            return Collections.emptyMap();
        }
        return jsonParts;
    }

    @Override
    public Request.Builder addPlainTextPart(String name, Object part) {
        if (plainTextParts == null) {
            plainTextParts = new LinkedHashMap<>();
        }
        if (part != null) {
            plainTextParts.put(name, part.toString());
            bodyType = BodyType.MULTIPART;
        }
        return this;
    }

    @Override
    public Map<String, String> plainTextParts() {
        if (plainTextParts == null) {
            return Collections.emptyMap();
        }
        return plainTextParts;
    }

    @Override
    public boolean multipartBody() {
        return bodyType == BodyType.MULTIPART;
    }

    @Override
    public String body() {
        return body;
    }

    @Override
    public Request.Builder emptyBody(boolean emptyBody) {
        if (emptyBody) {
            bodyType = BodyType.EMPTY_JSON;
        } else {
            bodyType = null;
        }
        return this;
    }

    @Override
    public boolean emptyBody() {
        return bodyType == BodyType.EMPTY_JSON;
    }

    @Override
    public Request.Builder urlEncodedBody(Map<String, String> formData) {
        this.formData = formData;
        return this;
    }

    @Override
    public Map<String, String> urlEncodedBody() {
        return formData;
    }

    @Override
    public <T> Response<T> send(HttpClient.ResponseHandler<T> responseHandler, List<Interceptor> interceptors) {
        for (Interceptor interceptor : interceptors) {
            if (interceptor != null) {
                interceptor.preHandle(this);
            }
        }

        Response<T> response = httpClient.send(this, responseHandler);

        for (Interceptor interceptor : interceptors) {
            if (interceptor != null) {
                 response = interceptor.postHandle(this, response);
            }
        }
        return response;
    }

    @Override
    public String content() {
        return httpClient.content(this);
    }
}
