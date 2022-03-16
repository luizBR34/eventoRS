# To add in Production (https://www.ibm.com/docs/pt-br/iococ?topic=kps-resolving-javasecuritycertcertificateexception-no-subject-alternative-names-present-error)
# WORKDIR /usr/local/share/ca-certificates
# ENV CERT "eventorscertificate.crt"
# RUN mkdir -p /usr/local/share/ca-certificates/certificate
# COPY $CERT /usr/local/share/ca-certificates/certificate
# RUN keytool -importcert -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts \
# -alias resourceserver -storepass changeit -noprompt -trustcacerts -file /usr/local/share/ca-certificates/certificate/$CERT
# RUN update-ca-certificates

FROM openjdk:8-jdk-alpine as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eventors.jar
RUN java -Djarmode=layertools -jar eventors.jar extract

FROM openjdk:8-jdk-alpine
EXPOSE 8084
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
