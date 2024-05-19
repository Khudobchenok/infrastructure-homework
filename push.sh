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

echo [Main app STARTING] pushing image $imageFullName...

echo [Main app] pushing image...
docker push $imageFullName

echo [Main app FINISHED] image $imageFullName pushed