package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Response;

import java.util.Map;
import java.util.function.UnaryOperator;

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

        boolean multipartBody();

        Builder headers(Map<String, String> headers);

        Map<String, String> headers();

        Builder header(String name, String value);

        <T> Response<T> send(Interceptor interceptor, HttpClient.ResponseHandler<T> responseHandler);

        default <T> Response<T> send(HttpClient.ResponseHandler<T> responseHandler) {
            return send(x -> x, responseHandler);
        }

        String content();

        @FunctionalInterface
        interface Interceptor extends UnaryOperator<Builder> {
        }
    }
}
