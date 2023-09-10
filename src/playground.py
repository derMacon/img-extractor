from flask import Flask, Blueprint
from flask_socketio import SocketIO
import cv2
import base64

app = Flask(__name__)
common_prefix = Blueprint('common_prefix', __name__, url_prefix='/api/v1')

socketio = SocketIO(app, cors_allowed_origins="*")


@common_prefix.route("/nav-history")
def show_nav_history():
    return 'works'

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
        except Exception as e:
            print(f"Error sending image: {e}")
            break


app.register_blueprint(common_prefix)

if __name__ == '__main__':
    image_path = 'path_to_your_image.jpg'  # Replace with the actual path to your image
    socketio.start_background_task(send_image, image_path)
    socketio.run(app)