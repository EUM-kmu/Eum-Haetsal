#!/bin/sh

port=`lsof -i :80 -t`

kill -9 $port
#git checkout dev
#git pull origin dev
cp src/main/resources/devapplication.yml src/main/resources/application.yml
#./gradlew build -x test
#docker-compose up --build -d
nohup ./gradlew bootRun > gradlew.log 2>&1 &
