package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.fiducia.mapper.FiduciaMapper;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationJson;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationMultipartBody;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.apache.http.protocol.HTTP.DATE_HEADER;

public class FiduciaPaymentInitiationService extends BasePaymentInitiationService {

    private final FiduciaMapper mapper = Mappers.getMapper(FiduciaMapper.class);

    public FiduciaPaymentInitiationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           Request.Builder.Interceptor requestBuilderInterceptor,
                                           LinksRewriter linksRewriter) {
        super(aspsp, httpClient, requestBuilderInterceptor, linksRewriter);
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                        String paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        if (body instanceof PeriodicPaymentInitiationJson) {
            body = mapper.toFiduciaPeriodicPaymentInitiationJson((PeriodicPaymentInitiationJson) body);
        }
        return super.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body);
    }

    @Override
    protected Class<?> getPaymentInitiationBodyClass(String paymentService) {
        if (PaymentService.PERIODIC_PAYMENTS.toString().equals(paymentService)) {
            return FiduciaPeriodicPaymentInitiationJson.class;
        }
        return super.getPaymentInitiationBodyClass(paymentService);
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(String paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {
        return getPeriodicPaymentInformation(paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            FiduciaPeriodicPaymentInitiationWithStatusResponse.class,
            mapper::toPeriodicPaymentInitiationWithStatusResponse);
    }

    @Override
    public Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(String paymentProduct,
                                                                                                 String paymentId,
                                                                                                 RequestHeaders requestHeaders,
                                                                                                 RequestParams requestParams) {
        return getPeriodicPain001PaymentInformation(paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            FiduciaPeriodicPaymentInitiationMultipartBody.class,
            mapper::toPeriodicPaymentInitiationMultipartBody);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }
}
