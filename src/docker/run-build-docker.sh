#!/bin/bash

docker build -t py-keylogger:v1.0 .

docker stop py-keylogger
docker rm py-keylogger

docker run --privileged --rm -t -d -u root --name py-keylogger py-keylogger:v1.0
docker logs -f py-keylogger
