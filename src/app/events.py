import cv2
import base64
from flask_socketio import SocketIO

socketio = SocketIO(cors_allowed_origins="*")

@socketio.on('connect')
def handle_connect():
    print('Client connected')

def send_image(image_path):
    while True:
        try:
            frame = cv2.imread(image_path)
            if frame is not None:
                _, img_encoded = cv2.imencode('.jpg', frame)
                image_data = base64.b64encode(img_encoded).decode('utf-8')
                socketio.emit('update_image', image_data)
                socketio.stop()
        except Exception as e:
            print(f"Error sending image: {e}")
            break
