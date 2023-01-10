FROM openjdk:17
ARG PASSWORD=local
ARG DATABASE=testing
ENV PASSWORD ${PASSWORD}
ENV DATABASE ${DATABASE}
# Copy jar file
COPY target/*.jar  /opt/auction-system-api-1.0.0-SNAPSHOT.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
ENTRYPOINT ["/usr/bin/bash", "/wrapper.sh", "PASSWORD=${PASSWORD}", "DATABASE=${DATABASE}"]