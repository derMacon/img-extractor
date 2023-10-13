from flask_socketio import SocketIO

socketio = SocketIO(cors_allowed_origins="*")


def send_image(navigator_dict):
    try:
        if navigator_dict is not None:
            socketio.emit('update_image', navigator_dict)
            socketio.stop()
    except Exception as e:
        print(f"Error sending image: {e}")
