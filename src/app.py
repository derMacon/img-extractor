from flask import Flask, jsonify, send_file, Blueprint, request, abort
from utils.logging_config import log
from logic.config_manager import ConfigManager

app = Flask(__name__)
common_prefix = Blueprint('common_prefix', __name__, url_prefix='/api/v1')

config_manager = ConfigManager()
navigator = config_manager.load_latest_nav()


@common_prefix.route("/")
def hello_world():
    # TODO
    return "<p>Hello, World!</p>"


@common_prefix.route("/nav-stats")
def nav_stats():
    return navigator.to_dict()


@common_prefix.route("/next-page")
def next_page():
    if navigator is None:
        log.debug('no active page navigator available')
    else:
        navigator.next_page()
        config_manager.overwrite_csv()
    return send_file(navigator.curr_page_img, mimetype='image/jpg')


@common_prefix.route("/previous-page")
def previous_page():
    if navigator is None:
        log.debug('no active page navigator available')
    else:
        navigator.previous_page()
        config_manager.overwrite_csv()
    return send_file(navigator.curr_page_img, mimetype='image/jpg')


@common_prefix.route("/go-to-page")
def goto_page():
    key = 'page_idx'
    page_idx = request.args.get(key)
    if page_idx is None:
        log.error("page idx key %s not in request args", key)
        abort(400, description=f"Missing required parameter: {key}")

    try:
        page_idx = int(page_idx)
    except ValueError:
        msg = f"given input parameter {key} with value {page_idx} cannot be parsed to int"
        log.debug(msg)
        abort(400, description=msg)

    if navigator is None:
        log.debug('no active page navigator available')
        abort(500, description='no active page navigator available')
    else:
        log.debug('page_idx: %s', page_idx)
        navigator.goto_page(page_idx)
        config_manager.overwrite_csv()

    return send_file(navigator.curr_page_img, mimetype='image/jpg')


# def update_hotkey_map(self, hotkey_map):
#     self.config_manager.update_hotkeys(hotkey_map)
#
#
# def create_nav(self, doc):
#     self.navigator = self.config_manager.create_nav(doc)
#
#
# def load_nav(self, doc):
#     self.navigator = self.config_manager.load_existing_nav(doc)
#

@common_prefix.route("/display-nav-history")
def display_nav_history():
    return [nav.to_dict() for nav in config_manager.nav_hist_stack]


#
#
# def teardown(self):
#     self.config_manager.teardown()

app.register_blueprint(common_prefix)
