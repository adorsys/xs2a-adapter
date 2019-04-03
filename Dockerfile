FROM adorsys/openjdk-jre-base:8-minideb

MAINTAINER https://github.com/adorsys/xs2a-gateway/

ENV SERVER_PORT 8081
ENV JAVA_OPTS -Xmx1024m
ENV JAVA_TOOL_OPTIONS -Xmx1024m

WORKDIR /opt/xs2a-gateway

COPY ./xs2a-gateway-app/target/xs2a-gateway-app.jar /opt/xs2a-gateway/xs2a-gateway-app.jar

EXPOSE 8081

CMD exec $JAVA_HOME/bin/java $JAVA_OPTS -jar /opt/xs2a-gateway/xs2a-gateway-app.jar