FROM python:3.10.13-bookworm

WORKDIR /app

RUN apt-get update && apt-get install -y xclip
COPY ./requirements.txt .
RUN pip install -r requirements.txt

COPY ./main.py .
COPY ./config.ini .
COPY ./src/ ./src/

#CMD python3 main.py
CMD tail -f /dev/null