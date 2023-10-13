from flask import Flask

from .events import socketio
from .routes import main


def create_app(debug=False):
    """Create an application."""
    app = Flask(__name__)
    app.debug = debug
    app.register_blueprint(main)

    socketio.init_app(app)
    return app
