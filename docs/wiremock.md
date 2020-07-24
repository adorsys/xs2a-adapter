##How to record wiremock stubs

1. Navigate to the destination directory for wiremock files
and start standalone wiremock server that will proxy requests to an aspsp

    `java -jar /path/to/wiremock-jre8-standalone-2.27.0.jar --port 8008`
2. Replace the aspsp url with wiremock server url (http://localhost:8008).
3. Set the replaced url as the target url of the wiremock recorder on [its UI web page](http://localhost:8008/__admin/recorder/)
and start recording as [usual](http://wiremock.org/docs/record-playback/).
