FROM eclipse-temurin:21-jre-alpine

LABEL org.opencontainers.image.source="https://github.com/adorsys/xs2a-adapter/"

ENV SERVER_PORT=8081
ENV JAVA_TOOL_OPTIONS="-Xmx1024m -Dpkcs12.keyStore=/opt/xs2a-adapter/test_keystore.p12"

WORKDIR /opt/xs2a-adapter

COPY xs2a-adapter-test/src/main/resources/de/adorsys/xs2a/adapter/test/test_keystore.p12 /opt/xs2a-adapter/test_keystore.p12
COPY xs2a-adapter-app/target/xs2a-adapter-app.jar /opt/xs2a-adapter/xs2a-adapter-app.jar
COPY adapters/comdirect-adapter/xs2a_sandbox_comdirect_de.crt xs2a_sandbox_comdirect_de.crt

USER 0
RUN chmod go+w /opt/xs2a-adapter
RUN keytool -import -trustcacerts -cacerts -alias comdirect -storepass changeit -file xs2a_sandbox_comdirect_de.crt -noprompt
USER 1001

EXPOSE 8081

CMD ["/opt/java/openjdk/bin/java","-jar","/opt/xs2a-adapter/xs2a-adapter-app.jar"]
