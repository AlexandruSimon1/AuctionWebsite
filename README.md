# README

### Auction BackEnd Application
* Version: 1.0
* Spring Boot Version 2.4.2
* Most up-to-date implementation will be found in the branch master.
* Final Project Software Development Academy Group Java Iasi 10
* For deployment add Environment Variables to configuration jasypt.encryptor.password=**** spring.profiles.active=local
* Username and Password for Database are encrypted with Jasypt for security purpose
* Jasypt Encrypt command CLI java -cp (path)\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar  org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="input" password=password algorithm=algorithm
