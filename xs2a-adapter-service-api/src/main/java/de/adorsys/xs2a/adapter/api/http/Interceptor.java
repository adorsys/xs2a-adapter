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

package de.adorsys.xs2a.adapter.api.http;

import de.adorsys.xs2a.adapter.api.Response;

public interface Interceptor {
    Request.Builder preHandle(Request.Builder builder);

    /**
     * the method will be executed when the response from ASPSP will be received.
     * @param builder request builder object
     * @param response response received from ASPSP
     * @return modified response object
     */
    default <T> Response<T> postHandle(Request.Builder builder, Response<T> response) {
        return response;
    }
}
