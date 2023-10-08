FROM ubuntu:22.04 as builder
#FROM python:3.11.6-alpine3.18
#FROM python:3.9-slim-bullseye
#FROM python:3.12.0-bookworm

WORKDIR /app

#COPY ./requirements.txt .
RUN apt-get update && apt-get install -y python3=3.10.6-1~22.04 python3.10-venv \
    && python3 -m venv /opt/venv && . /opt/venv/bin/activate

ENV PATH="/opt/venv/bin:$PATH"

RUN pip install Flask==2.3.2
RUN pip install Flask_Cors==4.0.0
RUN pip install Flask_SocketIO==5.3.6
RUN pip install opencv_python==4.8.0.76
RUN pip install Pillow==9.0.1
RUN pip install PyMuPDF==1.22.5
RUN pip install Requests==2.31.0
RUN pip install opencv-python-headless==4.8.1.78


# RUN apt-get install -y \
#    libgl1-mesa-glx=23.0.4-0ubuntu1~22.04.1 \
#    xauth=1:1.1-1build2 \
#    libglib2.0-0=2.72.4-0ubuntu2.2 \
#    && pip install -r requirements.txt


#ENTRYPOINT ["flask", "--app", "main.py", "run", "--debug"]


#FROM ubuntu:22.04
#FROM python:3.10.13-bookworm
#
#WORKDIR /app
#
#COPY --from=builder /opt/venv /opt/venv
##COPY --from=builder /usr/bin/python3 /usr/bin/python3
#ENV PATH="/opt/venv/bin:$PATH"
#
COPY ./main.py .
COPY ./src/ ./src/
COPY ./res/ ./res/
CMD python3 main.py

#EXPOSE 5000
##CMD apt-get update
##CMD flask --app main.py run --debug
#
#CMD tail -f /dev/null
