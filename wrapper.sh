#!/bin/bash
while ! exec 6<>/dev/tcp/${DATABASE_HOST}/${DATABASE_PORT}; do
    echo "Trying to connect to MySQL at ${DATABASE_HOST}:${DATABASE_PORT}..."
    sleep 10

done
echo ">> connected to MySQL database! <<"
java -Dspring.profiles.active=dev -Djasypt.encryptor.password=arthur -Djava.security.egd=file:/dev/./urandom -jar /opt/auction-spring-boot.jar