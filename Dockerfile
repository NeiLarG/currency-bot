FROM maven:3.8.6-openjdk-18 as maven
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package -Dmaven.test.skip && cp target/currencybot-*.jar bot.jar

FROM openjdk:18-jdk-alpine
COPY --from=maven /root/.m2 /root/.m2
WORKDIR /app 
COPY --from=maven /app/bot.jar ./bot.jar
ENTRYPOINT java -jar /app/bot.jar
