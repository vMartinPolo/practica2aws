docker rm -f master-mysql

mvn package -DskipTests

export BUCKET_NAME=practica-1.cloud.i.huertas.2021-v.martinp.2021
export REGION=eu-west-1

docker run --name master-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cloud-computing -e MYSQL_DATABASE=events_db -d  mysql:5.7
sleep 10

export RDS_ENDPOINT=localhost:3306
export RDS_DATABASE=events_db
export RDS_PASS=cloud-computing
export RDS_USER=root

java -jar -Dspring.profiles.active=production target/i.huertas.2021-v.martinp.2021-0.0.1-SNAPSHOT.jar