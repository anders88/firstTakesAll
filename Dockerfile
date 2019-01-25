FROM openjdk:8-jdk-alpine

COPY target/dependency /usr/src/firstTakesAll/dependency
COPY target/classes /usr/src/firstTakesAll/classes
COPY src/main/webapp /usr/src/firstTakesAll/src/main/webapp

WORKDIR /usr/src/firstTakesAll
CMD ["java", "-cp", "/usr/src/firstTakesAll/classes:/usr/src/firstTakesAll/dependency/*", "no.anderska.wta.WebServer"]
