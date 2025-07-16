cd src/common
mvn clean package -DskipTests
cd ../account
mvn clean package -DskipTests
cd ../..
docker compose build