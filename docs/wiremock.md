## How to record wiremock stubs

Assuming you're already familiar with the [wiremock recorder](http://wiremock.org/docs/record-playback/).
1. Navigate to the destination directory for wiremock files
and start a standalone wiremock server that will proxy requests to an aspsp
    ```
    java -jar /path/to/wiremock-jre8-standalone-2.27.0.jar --port 8008 \
    --https-truststore /path/to/tpp-cert.p12 \
    --truststore-type pkcs12 \
    --truststore-password <password> \
    --https-keystore /path/to/tpp-cert.p12 \
    --global-response-templating
    ```
2. Replace the aspsp url with the wiremock server url (http://localhost:8008)
in the adapter configuration.
3. Start recording with the replaced url as the target url, and a list of headers to capture.
    ```
    curl --location --request POST 'localhost:8008/__admin/recordings/start' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "targetBaseUrl": <aspsp url>,
        "captureHeaders": {
            "X-Request-ID": {},
            "Consent-ID": {},
            "Digest": {},
            "PSU-ID": {},
            "PSU-Corporate-ID": {},
            "TPP-Redirect-URI": {},
            "Date": {},
            "Signature": {},
            "TPP-Signature-Certificate": {},
            "PSU-IP-Address": {},
            "PSU-ID-Type": {},
            "PSU-Corporate-ID-Type": {},
            "TPP-Redirect-Preferred": {},
            "TPP-Nok-Redirect-URI": {},
            "TPP-Explicit-Authorisation-Preferred": {},
            "PSU-IP-Port": {},
            "PSU-Accept": {},
            "PSU-Accept-Charset": {},
            "PSU-Accept-Encoding": {},
            "PSU-Accept-Language": {},
            "PSU-User-Agent": {},
            "PSU-Http-Method": {},
            "PSU-Device-ID": {},
            "PSU-Geo-Location": {},
            "Content-Type": {},
            "Authorization": {}
        }
    }'
    ```
4. Stop recording using a `POST /__admin/recordings/stop` request
or [UI page](http://localhost:8008/__admin/recorder/) to flush the wiremock files on disk.
