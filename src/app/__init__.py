from flask import Flask
from flask_cors import CORS, cross_origin
from .routes import main
from .events import socketio


def create_app(debug=False):
    """Create an application."""
    app = Flask(__name__)

    app.debug = debug
    app.config['SECRET_KEY'] = 'gjr39dkjn344_!67#' # TODO do something about this (use .env)

    app.register_blueprint(main)

    socketio.init_app(app)
    return app
