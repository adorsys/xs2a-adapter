FROM adorsys/java:11

MAINTAINER https://github.com/adorsys/xs2a-adapter/

ENV SERVER_PORT 8081
ENV JAVA_OPTS -Xmx1024m
ENV JAVA_TOOL_OPTIONS -Xmx1024m

WORKDIR /opt/xs2a-adapter

COPY xs2a-adapter-app/target/xs2a-adapter-app.jar /opt/xs2a-adapter/xs2a-adapter-app.jar
COPY adapters/comdirect-adapter/xs2a_sandbox_comdirect_de.crt xs2a_sandbox_comdirect_de.crt

USER 0
RUN chmod go+w /opt/xs2a-adapter
RUN JAVA_LOCATION=$(readlink -f /etc/alternatives/java) \
    && JAVA_BIN=$(dirname "$JAVA_LOCATION") \
    && keytool -import -trustcacerts -keystore "$JAVA_BIN/../lib/security/cacerts" -alias comdirect -storepass changeit -file xs2a_sandbox_comdirect_de.crt -noprompt
USER 1001

EXPOSE 8081

CMD exec $JAVA_HOME/bin/java $JAVA_OPTS -jar /opt/xs2a-adapter/xs2a-adapter-app.jar
