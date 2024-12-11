mvn clean package -Dmaven.test.skip=true
docker build -t spring-ai-app:latest .
