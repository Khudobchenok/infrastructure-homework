#!/bin/bash
set -e

imageTag=$1
if [ -z "$1" ]
  then
    echo No image tag provided. latest will be used
    imageTag=latest
fi

repositoryName=730335383193.dkr.ecr.ap-southeast-2.amazonaws.com/people
imageFullName=$repositoryName:$imageTag

echo [Main App STARTING] building $imageFullName...

echo [Main App] creating jar...
java --version
(exec "./gradlew" :application:bootJar --no-daemon)

echo [Main App] creating docker image...
docker build --platform linux/arm64 -t $imageFullName .

echo [Main App FINISHED] image has been built