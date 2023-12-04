FROM openjdk:11.0.1-jdk
LABEL maintainer="prabhat@learnwithtechelly.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/demo-micrometer-prometheus-grafana-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} monitoring-demo.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/monitoring-demo.jar"]
