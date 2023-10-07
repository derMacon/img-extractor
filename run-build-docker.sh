#!/bin/bash

IMG_NAME=img-extractor
TAG=v1.0

docker build -t $IMG_NAME:$TAG .

docker stop $IMG_NAME
docker rm $IMG_NAME

docker run --rm -t -d -u root --name $IMG_NAME $IMG_NAME:$TAG
# docker run --privileged --rm -t -d -u root --name $IMG_NAME $IMG_NAME:$TAG
docker logs -f $IMG_NAME
