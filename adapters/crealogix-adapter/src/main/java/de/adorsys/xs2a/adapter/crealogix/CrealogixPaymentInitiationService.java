/*
 * Copyright 2018-2020 adorsys GmbH & Co KG
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

package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import org.mapstruct.factory.Mappers;

import static de.adorsys.xs2a.adapter.crealogix.CrealogixRequestResponseHandlers.crealogixRequestHandler;
import static de.adorsys.xs2a.adapter.crealogix.CrealogixRequestResponseHandlers.crealogixResponseHandler;
import static java.util.function.Function.identity;

public class CrealogixPaymentInitiationService extends BasePaymentInitiationService {

    private final CrealogixMapper mapper = Mappers.getMapper(CrealogixMapper.class);

    public CrealogixPaymentInitiationService(Aspsp aspsp,
                                             HttpClient httpClient,
                                             LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        return super.getPaymentInformation(PaymentService.PAYMENTS,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            ResponseHandlers.jsonResponseHandler(CrealogixPaymentInitiationWithStatusResponse.class))
                .map(mapper::toPaymentInitiationWithStatusResponse);
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        crealogixRequestHandler(requestHeaders);

        return super.initiatePayment(paymentService,
            paymentProduct,
            body,
            requestHeaders,
            requestParams,
            identity(),
            crealogixResponseHandler(PaymentInitationRequestResponse201.class));
    }
}
