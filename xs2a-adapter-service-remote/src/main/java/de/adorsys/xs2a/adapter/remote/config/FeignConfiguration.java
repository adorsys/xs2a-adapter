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

package de.adorsys.xs2a.adapter.remote.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.ContentType;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.BookingStatusCard;
import de.adorsys.xs2a.adapter.api.model.BookingStatusGeneric;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationMultipartBody;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.Xs2aHttpLogSanitizer;
import de.adorsys.xs2a.adapter.rest.impl.config.PeriodicPaymentInitiationMultipartBodyHttpMessageConverter;
import feign.Contract;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.CONTENT_TYPE;

@Configuration
public class FeignConfiguration {

    @Value("${xs2a-adapter.sanitizer.whitelist:recurringIndicator,validUntil,frequencyPerDay,combinedServiceIndicator}")
    private List<String> whitelist;

    @Bean
    HttpLogSanitizer httpLogSanitizer() {
        return new Xs2aHttpLogSanitizer(whitelist);
    }

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract(Collections.emptyList(), new CustomConversionService());
    }

    public static class CustomConversionService extends DefaultConversionService {

        public CustomConversionService() {

            addConverter(new Converter<BookingStatusGeneric, String>() {
                @Override
                public String convert(BookingStatusGeneric source) {
                    return source.toString();
                }
            });

            addConverter(new Converter<BookingStatusCard, String>() {
                @Override
                public String convert(BookingStatusCard source) {
                    return source.toString();
                }
            });

            addConverter(new Converter<PaymentProduct, String>() {
                @Override
                public String convert(PaymentProduct source) {
                    return source.toString();
                }
            });

            addConverter(new Converter<PaymentService, String>() {
                @Override
                public String convert(PaymentService source) {
                    return source.toString();
                }
            });
        }
    }

    @Bean
    Encoder encoder(ObjectFactory<HttpMessageConverters> messageConverters, ObjectMapper objectMapper) {
        PeriodicPaymentInitiationMultipartBodyHttpMessageConverter httpMessageConverter =
            new PeriodicPaymentInitiationMultipartBodyHttpMessageConverter(objectMapper);

        return new SpringEncoder(new SpringFormEncoder() {
            @Override
            public void encode(Object object, Type bodyType, RequestTemplate template) {
                if (!(object instanceof PeriodicPaymentInitiationMultipartBody)) {
                    super.encode(object, bodyType, template);
                    return;
                }
                BufferingOutputMessage outputMessage = new BufferingOutputMessage();
                try {
                    httpMessageConverter.write((PeriodicPaymentInitiationMultipartBody) object,
                        MediaType.MULTIPART_FORM_DATA,
                        outputMessage);
                } catch (IOException e) {
                    throw new EncodeException(e.getMessage(), e);
                }
                template.body(outputMessage.getOutputStream().toByteArray(), StandardCharsets.UTF_8);
                template.removeHeader(HttpHeaders.CONTENT_TYPE);
                template.header(HttpHeaders.CONTENT_TYPE, outputMessage.getHeaders().getContentType().toString());
            }
        }, messageConverters);
    }

    private static final class BufferingOutputMessage implements HttpOutputMessage {

        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        private final HttpHeaders httpHeaders = new HttpHeaders();

        @Override
        public OutputStream getBody() throws IOException {
            return this.outputStream;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        public ByteArrayOutputStream getOutputStream() {
            return this.outputStream;
        }

    }

    @Bean
    public Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters, HttpLogSanitizer logSanitizer) {
        return new OptionalDecoder(
            new ResponseEntityDecoder(new SpringDecoder(messageConverters) {
                @Override
                public Object decode(Response response, Type type) throws IOException {
                    Collection<String> contentTypeValues = response.headers().get(CONTENT_TYPE);
                    String contentType = null;
                    if (contentTypeValues != null) {
                        contentType = contentTypeValues.stream().findFirst().orElse(null);
                    }
                    if (contentType != null && contentType.startsWith(ContentType.MULTIPART_FORM_DATA)) {
                        return new ResponseHandlers(logSanitizer).multipartFormDataResponseHandler(PeriodicPaymentInitiationMultipartBody.class)
                            .apply(response.status(),
                                response.body().asInputStream(),
                                ResponseHeaders.fromMap(Collections.singletonMap(CONTENT_TYPE, contentType)));
                    }
                    return super.decode(response, type);
                }
            }));
    }
}
