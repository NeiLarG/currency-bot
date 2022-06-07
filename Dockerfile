FROM maven:3.8.5-openjdk-18 as maven_build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip

RUN mkdir -p target/docker-packaging && cd target/docker-packaging && jar -xf ../*.jar

FROM openjdk:18-jdk-alpine
WORKDIR /app

ARG DOCKER_PACKAGING_DIR=/app/target/docker-packaging
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/lib /app/lib
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/classes /app/classes
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/META-INF /app/META-INF

CMD java -cp .:classes:lib/* \
         -Djava.security.egd=file:/dev/./urandom \
         -Dspring.profiles.active=${PROFILE} \
         -Xms100m \
         -Xmx170m \
         com.neilarg.currencybot.CurrencybotApplication
