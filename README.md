# Project01

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 17](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org)
- [Docker](https://www.docker.com/)
- [Docker Componse](https://www.docker.com/)
- .H2 Database (Inbuild)


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `*.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn clean
mvn compile package -DskipTests=true
mvn spring-boot:run
```

## Run the application using docker-compose

```shell
docker-compose -f docker-compose.yaml build
docker-compose -f docker-compose.yaml up
docker-compose -f docker-compose.yaml down
```

## Run the application backend-service/frontend-service

## Backend-service (http://localhost:8080/swagger-ui/index.html#)
```shell
mvn clean
mvn compile package -DskipTests=true
mvn spring-boot:run
```

## Frontend-service (http://localhost:9090)
```shell
yarn
yarn build
yarn start
```
