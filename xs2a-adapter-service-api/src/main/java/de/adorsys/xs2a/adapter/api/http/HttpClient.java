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
import de.adorsys.xs2a.adapter.api.ResponseHeaders;

import java.io.InputStream;

public interface HttpClient {

    Request.Builder get(String uri);

    Request.Builder post(String uri);

    Request.Builder put(String uri);

    Request.Builder delete(String uri);

    <T> Response<T> send(Request.Builder requestBuilder, ResponseHandler<T> responseHandler);

    String content(Request.Builder requestBuilder);

    @FunctionalInterface
    interface ResponseHandler<T> {
        T apply(int statusCode, InputStream responseBody, ResponseHeaders responseHeaders);
    }
}
