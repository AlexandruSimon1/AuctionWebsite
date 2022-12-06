FROM openjdk:11
ARG PASSWORD=local
ARG DATABASE=testing
ENV PASSWORD ${PASSWORD}
ENV DATABASE ${DATABASE}
# Copy jar file
COPY target/*.jar  /opt/auction-spring-boot.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
ENTRYPOINT ["/usr/bin/bash", "/wrapper.sh", "PASSWORD=${PASSWORD}", "DATABASE=${DATABASE}"]