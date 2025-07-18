cd src/common
mvn clean install package -DskipTests
cd ../account
mvn clean install package -DskipTests
cd ../shop
mvn clean install package -DskipTests
cd ../order
mvn clean install package -DskipTests
cd ../..
docker compose build