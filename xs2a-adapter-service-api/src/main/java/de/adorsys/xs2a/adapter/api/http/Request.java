package de.adorsys.xs2a.adapter.api.http;

import de.adorsys.xs2a.adapter.api.Response;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Request {

    interface Builder {

        String method();

        String uri();

        String body();

        Builder jsonBody(String body);

        boolean jsonBody();

        Builder emptyBody(boolean empty);

        boolean emptyBody();

        Builder urlEncodedBody(Map<String, String> formData);

        Map<String, String> urlEncodedBody();

        Builder xmlBody(String body);

        boolean xmlBody();

        Builder addXmlPart(String name, String xmlPart);

        Map<String, String> xmlParts();

        Builder addJsonPart(String name, String jsonPart);

        Map<String, String> jsonParts();

        Builder addPlainTextPart(String name, Object part);

        Map<String, String> plainTextParts();

        boolean multipartBody();

        Builder headers(Map<String, String> headers);

        Map<String, String> headers();

        Builder header(String name, String value);

        @Deprecated
        default <T> Response<T> send(HttpClient.ResponseHandler<T> responseHandler, Interceptor... interceptors) {
            return send(responseHandler, Arrays.asList(interceptors));
        }

        <T> Response<T> send(HttpClient.ResponseHandler<T> responseHandler, List<Interceptor> interceptors);

        @Deprecated
        default <T> Response<T> send(Interceptor interceptor, HttpClient.ResponseHandler<T> responseHandler) {
            return send(responseHandler, interceptor);
        }

        default <T> Response<T> send(HttpClient.ResponseHandler<T> responseHandler) {
            return send(responseHandler, Collections.emptyList());
        }

        String content();
    }
}
