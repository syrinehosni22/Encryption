FROM eclipse-temurin:17-jdk-jammy

EXPOSE 8081
ADD target/Encryption-App-0.0.1.jar Encryption-App-0.0.1.jar
ENTRYPOINT ["java","-jar","/Encryption-App-0.0.1.jar"]
