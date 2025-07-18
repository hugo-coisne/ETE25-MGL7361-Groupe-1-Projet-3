cd src/common
mvn clean install package -DskipTests
cd ../user
mvn clean install package -DskipTests
cd ../shop
mvn clean install package -DskipTests
cd ../order
mvn clean install package -DskipTests
cd ../payment
mvn clean install package -DskipTests
cd ../delivery
mvn clean install package -DskipTests
cd ../..
docker compose build