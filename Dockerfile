FROM openjdk:11-oracle
COPY "./target/bank-account-service-1.0.0.jar" "/app/bank-account-service-1.0.0.jar"
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/bank-account-service-1.0.0.jar"]