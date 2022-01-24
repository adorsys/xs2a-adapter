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

package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.ContentType;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditUpdatePsuAuthenticationResponse;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

public class UnicreditPaymentInitiationService extends BasePaymentInitiationService {

    private static final String DEFAULT_COUNTRY_CODE = "DE";
    private final UnicreditMapper unicreditMapper = Mappers.getMapper(UnicreditMapper.class);

    public UnicreditPaymentInitiationService(Aspsp aspsp,
                                             HttpClientFactory httpClientFactory,
                                             LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        Object requestBody = null;
        if (!isXml(paymentProduct)) {
            Class<?> paymentBodyClass = getPaymentInitiationBodyClass(paymentService);
            requestBody = jsonMapper.convertValue(body, paymentBodyClass);
            addCreditorAddress(requestBody);
        }

        return super.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, requestBody == null ? body : requestBody);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            UnicreditUpdatePsuAuthenticationResponse.class,
            unicreditMapper::toUpdatePsuAuthenticationResponse);
    }

    private void addCreditorAddress(Object body) {
        if (body instanceof PaymentInitiationJson) {
            PaymentInitiationJson paymentsJson = (PaymentInitiationJson) body;
            if (paymentsJson.getCreditorAddress() == null) {
                paymentsJson.setCreditorAddress(buildDefaultAddress());
            }
        } else if (body instanceof PeriodicPaymentInitiationJson) {
            PeriodicPaymentInitiationJson periodicJson = (PeriodicPaymentInitiationJson) body;
            if (periodicJson.getCreditorAddress() == null) {
                periodicJson.setCreditorAddress(buildDefaultAddress());
            }
        }
    }

    private Address buildDefaultAddress() {
        Address address = new Address();
        address.setCountry(DEFAULT_COUNTRY_CODE);
        return address;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> addPsuIdTypeHeader(Map<String, String> headers) {
        return UnicreditHeaders.addPsuIdTypeHeader(headers);
    }

    @Override
    public List<ValidationError> validateInitiatePayment(PaymentService paymentService,
                                                         PaymentProduct paymentProduct,
                                                         RequestHeaders requestHeaders,
                                                         RequestParams requestParams,
                                                         Object body) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                   PaymentProduct paymentProduct,
                                                                   String paymentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                   PaymentProduct paymentProduct,
                                                                   String paymentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication updatePsuAuthentication) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }
}
