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
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.mapstruct.factory.Mappers;

import static java.util.function.Function.identity;

public class CrealogixPaymentInitiationService extends BasePaymentInitiationService {

    private final CrealogixMapper mapper = Mappers.getMapper(CrealogixMapper.class);
    private final CrealogixRequestResponseHandlers requestResponseHandlers;

    public CrealogixPaymentInitiationService(Aspsp aspsp,
                                             HttpClientFactory httpClientFactory,
                                             LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
        HttpClientConfig config = httpClientFactory.getHttpClientConfig();
        this.requestResponseHandlers = new CrealogixRequestResponseHandlers(config.getLogSanitizer());
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        return super.initiatePayment(paymentService,
            paymentProduct,
            body,
            requestHandler(requestHeaders),
            requestParams,
            identity(),
            requestResponseHandlers.crealogixResponseHandler(PaymentInitationRequestResponse201.class));
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        return super.getPaymentInformation(PaymentService.PAYMENTS,
            paymentProduct,
            paymentId,
            requestHandler(requestHeaders),
            requestParams,
            requestResponseHandlers.jsonResponseHandler(CrealogixPaymentInitiationWithStatusResponse.class))
                .map(mapper::toPaymentInitiationWithStatusResponse);
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {

        return super.getPeriodicPaymentInformation(paymentProduct, paymentId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                                                 String paymentId,
                                                                                                 RequestHeaders requestHeaders,
                                                                                                 RequestParams requestParams) {

        return super.getPeriodicPain001PaymentInformation(paymentProduct, paymentId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<String> getPaymentInformationAsString(PaymentService paymentService,
                                                          PaymentProduct paymentProduct,
                                                          String paymentId,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams) {

        return super.getPaymentInformationAsString(paymentService,
            paymentProduct,
            paymentId,
            requestHandler(requestHeaders),
            requestParams);
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {

        return super.getPaymentInitiationScaStatus(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHandler(requestHeaders),
            requestParams);
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(PaymentService paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {

        return super.getPaymentInitiationStatus(paymentService,
            paymentProduct,
            paymentId,
            requestHandler(requestHeaders),
            requestParams);
    }

    @Override
    public Response<String> getPaymentInitiationStatusAsString(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {

        return super.getPaymentInitiationStatusAsString(paymentService,
            paymentProduct,
            paymentId,
            requestHandler(requestHeaders),
            requestParams);
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {

        return super.getPaymentInitiationAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHandler(requestHeaders),
            requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {

        return super.startPaymentAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHandler(requestHeaders),
            requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {

        return super.startPaymentAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHandler(requestHeaders),
            requestParams,
            updatePsuAuthentication);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {

        return super.updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHandler(requestHeaders),
            requestParams,
            updatePsuAuthentication);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {

        return super.updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHandler(requestHeaders),
            requestParams,
            selectPsuAuthenticationMethod);
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                            PaymentProduct paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {

        return super.updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHandler(requestHeaders),
            requestParams,
            transactionAuthorisation);
    }

    private RequestHeaders requestHandler(RequestHeaders requestHeaders) {
        return requestResponseHandlers.crealogixRequestHandler(requestHeaders);
    }
}
