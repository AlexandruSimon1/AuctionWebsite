FROM openjdk:14
ARG PASSWORD=local
ENV PASSWORD ${PASSWORD}
# Copy jar file
COPY target/*.jar  /opt/auction-spring-boot.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
ENTRYPOINT ["/usr/bin/bash", "/wrapper.sh", "PASSWORD=${PASSWORD}"]
#RUN bash -c 'java -Dspring.profiles.active=dev -Djasypt.encryptor.password=${PASSWORD} -Djava.security.egd=file:/dev/./urandom -jar /opt/auction-spring-boot.jar'