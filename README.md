= oclet-phonenumber-microservice =
:docs: https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference
:github: https://github.com/spring-projects/spring-boot

Oclet's phonenumber microservice allows the user to do the following:
- Fetch all customers' phone numbers
- Fetch a specific customer's phone number
- Activate a phone number by adding the phone number to the customer's metadata in the underlying datastore.

Installation and Getting Started
Assembled with JDK11

To start assemble the JAR 
```mvn clean install```

to start up the tomcat server with springboot running
```mvn spring-boot:run```
