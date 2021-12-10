FROM openjdk:14
#FROM bash:4.4
#FROM alpine:3.7
ARG PASSWORD=local
ENV PASSWORD${PASSWORD}
# Copy jar file
COPY target/*.jar  /opt/auction-spring-boot.jar
ADD wrapper.sh wrapper.sh
#In case if we test docker locally with using docker MySQL Database
#RUN bash -c 'chmod +x /wrapper.sh'
#ENTRYPOINT ["/bin/bash", "/wrapper.sh"]

RUN bash -c 'java -Dspring.profiles.active=dev -Djasypt.encryptor.password=${PASSWORD} -Djava.security.egd=file:/dev/./urandom -jar /opt/auction-spring-boot.jar'