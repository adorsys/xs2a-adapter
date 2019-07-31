FROM adorsys/java:8

MAINTAINER https://github.com/adorsys/xs2a-adapter/

ENV SERVER_PORT 8081
ENV JAVA_OPTS -Xmx1024m \
    # todo remove when fixed deutsche bank sandbox is sending incomplete certificate chain
    -Dcom.sun.security.enableAIAcaIssuers=true

ENV JAVA_TOOL_OPTIONS -Xmx1024m

WORKDIR /opt/xs2a-adapter

COPY xs2a-adapter-app/target/xs2a-adapter-app.jar /opt/xs2a-adapter/xs2a-adapter-app.jar

USER 0
RUN chmod o+w /opt/xs2a-adapter
USER 1001

EXPOSE 8081

CMD exec $JAVA_HOME/bin/java $JAVA_OPTS $SSL_OPTS -jar /opt/xs2a-adapter/xs2a-adapter-app.jar
