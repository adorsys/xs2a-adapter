FROM openjdk:8-alpine

WORKDIR /service
ENV JAVA_OPTS ""
ENV SERVICE_PARAMS ""
ADD xs2a-gateway-app/target/xs2a-gateway-app.jar /service/
CMD java $JAVA_OPTS -jar xs2a-gateway-app.jar $SERVICE_PARAMS