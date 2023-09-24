import cv2
import base64
from flask_socketio import SocketIO
from utils.logging_config import log

socketio = SocketIO(cors_allowed_origins="*")


@socketio.on('connect')
def handle_connect():
    print('Client connected')


def send_image(navigator_dict):
    while True:
        try:
            if navigator_dict is not None:
                socketio.emit('update_image', navigator_dict)
                socketio.stop()
        except Exception as e:
            print(f"Error sending image: {e}")
            break
