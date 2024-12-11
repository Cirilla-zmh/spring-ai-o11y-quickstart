FROM eclipse-temurin:17-jre

ADD target/observability-example-0.0.1-SNAPSHOT.jar /app.jar
ADD agent/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

ENTRYPOINT java -jar -javaagent:/opentelemetry-javaagent.jar /app.jar
