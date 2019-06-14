package de.adorsys.xs2a.adapter.signing.util;

public final class Constants {
    // header names:
    public static final String DIGEST_HEADER_NAME = "Digest";
    public static final String SIGNATURE_HEADER_NAME = "Signature";
    public static final String TPP_SIGNATURE_CERTIFICATE_HEADER_NAME = "TPP-Signature-Certificate";

    // signature header attributes names:
    public static final String KEY_ID_ATTRIBUTE_NAME = "keyId";
    public static final String ALGORITHM_ATTRIBUTE_NAME = "algorithm";
    public static final String HEADERS_ATTRIBUTE_NAME = "headers";
    public static final String SIGNATURE_ATTRIBUTE_NAME = "signature";

    // separators:
    public static final String EQUALS_SIGN_SEPARATOR = "=";
    public static final String COLON_SEPARATOR = ":";
    public static final String COMMA_SEPARATOR = ",";
    public static final String QUOTE_SEPARATOR = "\"";
    public static final String SPACE_SEPARATOR = " ";
    public static final String HEXADECIMAL_SPACE_SEPARATOR = "%20";
    public static final String LINE_BREAK_SEPARATOR = "\n";
    public static final String CARRIAGE_RETURN_SEPARATOR = "\r";

    // certificates:
    public static final String CERTIFICATE_SERIAL_NUMBER_ATTRIBUTE = "SN";
    public static final String CERTIFICATION_AUTHORITY_ATTRIBUTE = "CA";
    public static final String BEGIN_CERTIFICATE_LABEL = "-----BEGIN CERTIFICATE-----";
    public static final String END_CERTIFICATE_LABEL = "-----END CERTIFICATE-----";

    // key storage system properties:
    public static final String KEY_PATH_SYSTEM_PROPERTY = "javax.net.ssl.keyStore";
    public static final String KEYSTORE_PASSWORD_SYSTEM_PROPERTY = "javax.net.ssl.keyStorePassword";
    public static final String KEYSTORE_TYPE_SYSTEM_PROPERTY = "javax.net.ssl.keyStoreType";

    private Constants() {
    }
}
