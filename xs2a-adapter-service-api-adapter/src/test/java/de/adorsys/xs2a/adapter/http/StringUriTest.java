package de.adorsys.xs2a.adapter.http;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUriTest {

    @Test
    public void fromElements() {
        String uri = StringUri.fromElements("/a", "b/", "/c/");
        assertThat(uri).isEqualTo("a/b/c");

        uri = StringUri.fromElements("/d/", "e", "/f/", "g/");
        assertThat(uri).isEqualTo("d/e/f/g");
    }

    @Test
    public void withQueryIgnoresParametersWithNullValues() {
        assertThat(StringUri.withQuery("http://example.com", singletonMap("q", null)))
            .isEqualTo("http://example.com");
    }

    @Test
    public void withQueryCallsToStringOnValues() {
        assertThat(StringUri.withQuery("http://example.com", singletonMap("q", LocalDate.of(2012, 12, 21))))
            .isEqualTo("http://example.com?q=2012-12-21");
    }

    @Test
    public void getQueryParamsFromUriWithoutParams() {
        assertThat(StringUri.getQueryParamsFromUri("http://example.com"))
            .isEmpty();
    }

    @Test
    public void getQueryParamsFromUriWithOneParam() {
        String key = "q";
        String value = "2012-12-21";
        String uri = String.format("http://example.com?%s=%s", key, value);

        Map<String, String> params = StringUri.getQueryParamsFromUri(uri);

        assertThat(params.size()).isEqualTo(1);

        String actualValue = params.get(key);
        assertThat(actualValue).isEqualTo(value);
    }

    @Test
    public void getQueryParamsFromUriWithMoreThenOneParam() {
        String key1 = "q";
        String value1 = "2012-12-21";
        String key2 = "k";
        String value2 = "someKey";
        String key3 = "zz";
        String value3 = "someKeyZZ";

        String uri = String.format("http://example.com?%s=%s&%s=%s&%s=%s",
            key1, value1, key2, value2, key3, value3);

        Map<String, String> params = StringUri.getQueryParamsFromUri(uri);

        assertThat(params.size()).isEqualTo(3);

        String actualValue1 = params.get(key1);
        assertThat(actualValue1).isEqualTo(value1);

        String actualValue2 = params.get(key2);
        assertThat(actualValue2).isEqualTo(value2);

        String actualValue3 = params.get(key3);
        assertThat(actualValue3).isEqualTo(value3);
    }

    @Test
    public void isUriStartsWithSlash() {
        String uri = "/some/uri";
        assertThat(StringUri.isUri(uri)).isTrue();
    }

    @Test
    public void isUriStartsWithVersion() {
        String uri = "v2/some/uri";
        assertThat(StringUri.isUri(uri)).isTrue();
    }

    @Test
    public void isUriDoesNotStartWithVersionAndSlash() {
        String uri = "example.com";
        assertThat(StringUri.isUri(uri)).isFalse();
    }

    @Test
    public void isUriStartsWithProtocol() {
        String uri = "http://example.com";
        assertThat(StringUri.isUri(uri)).isFalse();
    }

    @Test
    public void containsProtocol() {
        String uriTemplate = "%s://example.com";

        assertThat(StringUri.containsProtocol(String.format(uriTemplate, "https"))).isTrue();
        assertThat(StringUri.containsProtocol(String.format(uriTemplate, "http"))).isTrue();
        assertThat(StringUri.containsProtocol(String.format(uriTemplate, "ftp"))).isTrue();
        assertThat(StringUri.containsProtocol(String.format(uriTemplate, "anyProtocol"))).isTrue();
    }

    @Test
    public void containsProtocolWithoutProtocol() {
        String uri = "www.example.com";
        assertThat(StringUri.containsProtocol(uri)).isFalse();
    }

    @Test
    public void decodeUri() throws UnsupportedEncodingException {
        String uri = "http://example.com/path?param1=value1&param2=value2";

        String actual = StringUri.decodeUrl(URLEncoder.encode(uri, StandardCharsets.UTF_8.name()));
        assertThat(actual).isEqualTo(uri);
    }

    @Test
    public void containsQueryParam() {
        String uri = "http://example.com/path?param1=value1&param2=value2";

        assertThat(StringUri.containsQueryParam(uri, "param1")).isTrue();
        assertThat(StringUri.containsQueryParam(uri, "param2")).isTrue();
        assertThat(StringUri.containsQueryParam(uri, "param3")).isFalse();
    }

    @Test
    public void appendQueryParam() {
        String uri1 = "http://example.com/path?param1=value1&param2=value2";
        String uri2 = "http://example.com/path";

        String actual1 = StringUri.appendQueryParam(uri1, "param3", "value3");
        Map<String, String> params1 = StringUri.getQueryParamsFromUri(actual1);
        assertThat(params1.size()).isEqualTo(3);
        assertThat(params1.get("param1")).isEqualTo("value1");
        assertThat(params1.get("param2")).isEqualTo("value2");
        assertThat(params1.get("param3")).isEqualTo("value3");

        String actual2 = StringUri.appendQueryParam(uri1, "param2", "value2");
        Map<String, String> params2 = StringUri.getQueryParamsFromUri(actual2);
        assertThat(params2.size()).isEqualTo(2);
        assertThat(params2.get("param1")).isEqualTo("value1");
        assertThat(params2.get("param2")).isEqualTo("value2");

        String actual3 = StringUri.appendQueryParam(uri1, "param2", "value3");
        Map<String, String> params3 = StringUri.getQueryParamsFromUri(actual3);
        assertThat(params3.size()).isEqualTo(2);
        assertThat(params3.get("param1")).isEqualTo("value1");
        assertThat(params3.get("param2")).isEqualTo("value3");

        String actual4 = StringUri.appendQueryParam(uri2, "param", "value");
        Map<String, String> params4 = StringUri.getQueryParamsFromUri(actual4);
        assertThat(params4.size()).isEqualTo(1);
        assertThat(params4.get("param")).isEqualTo("value");
    }

    @Test
    public void removeAllQueryParams() {
        String uri1 = "http://example.com/path?param1=value1&param2=value2";
        String uri2 = "http://example.com/path";

        assertThat(StringUri.removeAllQueryParams(uri1)).isEqualTo("http://example.com/path");
        assertThat(StringUri.removeAllQueryParams(uri2)).isEqualTo("http://example.com/path");
    }
}
