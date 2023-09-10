from flask import Blueprint

main = Blueprint('main', __name__, url_prefix='/api/v1')

from . import routes, events