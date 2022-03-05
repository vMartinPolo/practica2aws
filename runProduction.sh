
export BUCKET_NAME=i.huertas.2021-v.martinp.2021
export REGION=eu-west-1
export RDS_ENDPOINT=events.cz7almbrlmf9.eu-west-1.rds.amazonaws.com
export RDS_DATABASE=events_db
export RDS_USER=admin
export RDS_PASS=password

java -jar -Dspring.profiles.active=production /home/ubuntu/app.jar
