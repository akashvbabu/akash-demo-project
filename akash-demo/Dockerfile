# Build the package
FROM maven:3.6.3-jdk-11 as build

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Run the package
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/akash-demo-0.0.1-SNAPSHOT.jar /usr/local/jars/akash-demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/jars/akash-demo.jar"]