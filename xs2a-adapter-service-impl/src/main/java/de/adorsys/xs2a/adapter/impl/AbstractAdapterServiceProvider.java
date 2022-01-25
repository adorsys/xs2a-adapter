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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.api.PaymentInitiationServiceProvider;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockStubDifferenceDetectingInterceptor;

import java.util.*;

public abstract class AbstractAdapterServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private boolean wiremockValidationEnabled;

    @Override
    public void wiremockValidationEnabled(boolean value) {
        this.wiremockValidationEnabled = value;
    }

    public List<Interceptor> getInterceptors(Aspsp aspsp, Interceptor... interceptors) {
        List<Interceptor> list = new ArrayList<>(Optional.ofNullable(interceptors)
                                                     .map(Arrays::asList)
                                                     .orElseGet(Collections::emptyList));
        if (wiremockValidationEnabled && WiremockStubDifferenceDetectingInterceptor.isWiremockSupported(aspsp.getAdapterId())) {
            list.add(new WiremockStubDifferenceDetectingInterceptor(aspsp));
            return Collections.unmodifiableList(list);
        }
        return list;
    }
}
