from flask import Flask, jsonify, send_file, Blueprint
from utils.logging_config import log
from logic.config_manager import ConfigManager

app = Flask(__name__)
common_prefix = Blueprint('common_prefix', __name__, url_prefix='/api/v1')

config_manager = ConfigManager()
navigator = config_manager.load_latest_nav()


@common_prefix.route("/")
def hello_world():
    return "<p>Hello, World!y....</p>"


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


# @app.route("/go-to-page")
# def goto_page(self, page_idx):
#     if self.navigator is None:
#         log.debug('no active page navigator available')
#     else:
#         self.navigator.goto_page(page_idx)
#         self.config_manager.overwrite_csv()
#     return send_file(navigator.curr_page_img, mimetype='image/jpg')


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
#
# def display_nav_history(self):
#     return self.config_manager.nav_hist_stack
#
#
# def teardown(self):
#     self.config_manager.teardown()

app.register_blueprint(common_prefix)
