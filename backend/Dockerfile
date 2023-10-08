FROM ubuntu:22.04 as builder

WORKDIR /app

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

# HINT: for some reason an error gets thrown when I try to install it via the requirements.txt
#       no idea why, when using the direct pip install it seems to work
#COPY ./requirements.txt .
#RUN pip install -r requirements.txt

# HINT: I tried to create a multi stage build because some dependencies cannot be installed
#       when using the python base image (e.g. the PyMuPDF). The idea was to build an virtual
#       env in the first builder step using ubuntu and then copy the whole venv directory to the
#       final build stage. But this won't work because the second stage cannot activate a venv
#       which it didn't create itself.
#FROM python:3.10.13-bookworm
#COPY --from=builder /opt/venv /opt/venv
##COPY --from=builder /usr/bin/python3 /usr/bin/python3
#ENV PATH="/opt/venv/bin:$PATH"

COPY ./main.py .
COPY ./src/ ./src/
COPY ./res/ ./res/

EXPOSE 5000
CMD python3 main.py

#CMD tail -f /dev/null
