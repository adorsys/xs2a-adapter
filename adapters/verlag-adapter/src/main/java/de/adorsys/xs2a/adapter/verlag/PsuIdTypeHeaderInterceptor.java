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

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID_TYPE;

public class PsuIdTypeHeaderInterceptor implements Interceptor {

    @Override
    public Request.Builder preHandle(Request.Builder builder) {
        return handlePsuIdTypeHeader(builder);
    }

    private Request.Builder handlePsuIdTypeHeader(Request.Builder builder) {
        if (builder.headers().containsKey(PSU_ID_TYPE)) {
            String psuIdType = builder.headers().get(PSU_ID_TYPE);
            if (StringUtils.isBlank(psuIdType)) {
                builder.headers().remove(PSU_ID_TYPE);
            }
        }

        return builder;
    }
}
