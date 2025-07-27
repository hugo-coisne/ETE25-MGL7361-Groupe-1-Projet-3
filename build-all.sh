cd src/common
mvn clean install package -DskipTests
cd ../user
mvn clean install package -DskipTests
cd ../shop
mvn clean install package -DskipTests
cd ../order
mvn clean install package -DskipTests
cd ../checkout
mvn clean install package -DskipTests
cd ../delivery
mvn clean install package -DskipTests
cd ../main
mvn clean install package -DskipTests
cd ../..
docker compose build