package de.adorsys.xs2a.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationMultipartBody;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationXmlPart2StandingorderTypeJson;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @see FormHttpMessageConverter
 */
public class PeriodicPaymentInitiationMultipartBodyHttpMessageConverter
    implements HttpMessageConverter<PeriodicPaymentInitiationMultipartBody> {

    private final List<MediaType> supportedMediaTypes = Collections.singletonList(MediaType.MULTIPART_FORM_DATA);

    private final List<HttpMessageConverter<?>> partConverters = new ArrayList<>();

    private final Charset charset = StandardCharsets.UTF_8;

    public PeriodicPaymentInitiationMultipartBodyHttpMessageConverter(ObjectMapper objectMapper) {
        this.partConverters.add(new StringHttpMessageConverter());
        this.partConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return this.supportedMediaTypes;
    }

    @Override
    public boolean canRead(Class<?> clazz, @Nullable MediaType mediaType) {
        // reading is not supported by the spring framework: a multipart request doesn't have a body
        // see AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters
        return false;
    }

    @Override
    public PeriodicPaymentInitiationMultipartBody read(
        Class<? extends PeriodicPaymentInitiationMultipartBody> clazz,
        HttpInputMessage inputMessage
    ) throws IOException, HttpMessageNotReadableException {

        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType) {
        return PeriodicPaymentInitiationMultipartBody.class.isAssignableFrom(clazz);
    }

    @Override
    public void write(PeriodicPaymentInitiationMultipartBody body,
                      MediaType contentType,
                      HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        if (contentType == null) {
            contentType = MediaType.MULTIPART_FORM_DATA;
        }

        byte[] boundary = generateMultipartBoundary();
        Map<String, String> parameters = new LinkedHashMap<>(2);
        parameters.put("charset", this.charset.name());
        parameters.put("boundary", new String(boundary, StandardCharsets.US_ASCII));

        contentType = new MediaType(contentType, parameters);
        outputMessage.getHeaders().setContentType(contentType);

        if (outputMessage instanceof StreamingHttpOutputMessage) {
            StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) outputMessage;
            streamingOutputMessage.setBody(outputStream -> {
                writeParts(outputStream, body, boundary);
                writeEnd(outputStream, boundary);
            });
        } else {
            writeParts(outputMessage.getBody(), body, boundary);
            writeEnd(outputMessage.getBody(), boundary);
        }
    }

    protected byte[] generateMultipartBoundary() {
        return MimeTypeUtils.generateMultipartBoundary();
    }

    private void writeParts(OutputStream os,
                            PeriodicPaymentInitiationMultipartBody body,
                            byte[] boundary) throws IOException {
        writeBoundary(os, boundary);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<Object> xmlPart = new HttpEntity<>(body.getXml_sct(), headers);
        writePart("xml_sct", xmlPart, os);
        writeNewLine(os);

        writeBoundary(os, boundary);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PeriodicPaymentInitiationXmlPart2StandingorderTypeJson> jsonPart =
            new HttpEntity<>(body.getJson_standingorderType(), headers);
        writePart("json_standingorderType", jsonPart, os);
        writeNewLine(os);
    }

    @SuppressWarnings("unchecked")
    private void writePart(String name, HttpEntity<?> partEntity, OutputStream os) throws IOException {
        Object partBody = partEntity.getBody();
        if (partBody == null) {
            throw new IllegalStateException("Empty body for part '" + name + "': " + partEntity);
        }
        Class<?> partType = partBody.getClass();
        HttpHeaders partHeaders = partEntity.getHeaders();
        MediaType partContentType = partHeaders.getContentType();
        for (HttpMessageConverter<?> messageConverter : this.partConverters) {
            if (messageConverter.canWrite(partType, partContentType)) {
                HttpOutputMessage multipartMessage = new MultipartHttpOutputMessage(os, this.charset);
                multipartMessage.getHeaders().setContentDispositionFormData(name, null);
                if (!partHeaders.isEmpty()) {
                    multipartMessage.getHeaders().putAll(partHeaders);
                }
                ((HttpMessageConverter<Object>) messageConverter).write(partBody, partContentType, multipartMessage);
                return;
            }
        }
        throw new HttpMessageNotWritableException("Could not write request: no suitable HttpMessageConverter " +
            "found for request type [" + partType.getName() + "]");
    }

    private void writeBoundary(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        writeNewLine(os);
    }

    private static void writeEnd(OutputStream os, byte[] boundary) throws IOException {
        os.write('-');
        os.write('-');
        os.write(boundary);
        os.write('-');
        os.write('-');
        writeNewLine(os);
    }

    private static void writeNewLine(OutputStream os) throws IOException {
        os.write('\r');
        os.write('\n');
    }

    private static class MultipartHttpOutputMessage implements HttpOutputMessage {

        private final OutputStream outputStream;

        private final Charset charset;

        private final HttpHeaders headers = new HttpHeaders();

        private boolean headersWritten = false;

        MultipartHttpOutputMessage(OutputStream outputStream, Charset charset) {
            this.outputStream = outputStream;
            this.charset = charset;
        }

        @Override
        public HttpHeaders getHeaders() {
            return (this.headersWritten ? HttpHeaders.readOnlyHttpHeaders(this.headers) : this.headers);
        }

        @Override
        public OutputStream getBody() throws IOException {
            writeHeaders();
            return this.outputStream;
        }

        private void writeHeaders() throws IOException {
            if (!this.headersWritten) {
                for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                    byte[] headerName = getBytes(entry.getKey());
                    for (String headerValueString : entry.getValue()) {
                        byte[] headerValue = getBytes(headerValueString);
                        this.outputStream.write(headerName);
                        this.outputStream.write(':');
                        this.outputStream.write(' ');
                        this.outputStream.write(headerValue);
                        writeNewLine(this.outputStream);
                    }
                }
                writeNewLine(this.outputStream);
                this.headersWritten = true;
            }
        }

        private byte[] getBytes(String name) {
            return name.getBytes(this.charset);
        }
    }
}
