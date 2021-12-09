FROM openjdk:14
#FROM bash:4.4
#FROM alpine:3.7
ENV PASSWORD ${PASSWORD}
# Copy jar file
COPY target/*.jar  /opt/auction-spring-boot.jar
ADD wrapper.sh wrapper.sh
#RUN apk add --no-cache bash
RUN bash -c 'chmod +x /wrapper.sh'
ENTRYPOINT ["/bin/bash", "/wrapper.sh"]
