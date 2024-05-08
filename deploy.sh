#!/bin/sh

port=`lsof -i :8000 -t`

kill -9 $port
git checkout dev
git pull origin dev
nohup ./gradlew bootRun > gradlew.log 2>&1 &
