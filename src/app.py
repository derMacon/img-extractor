from flask import Flask, jsonify, send_file, Blueprint, request, abort

from logic.controller import Controller
from utils.logging_config import log

app = Flask(__name__)
common_prefix = Blueprint('common_prefix', __name__, url_prefix='/api/v1')

controller = Controller()

@common_prefix.route("/")
def hello_world():
    # TODO
    return "<p>Hello, World!</p>"


@common_prefix.route("/nav-stats")
def nav_stats():
    return controller.get_nav_stats()


@common_prefix.route("/next-page")
def next_page():
    controller.next_page()
    return send_file(controller.get_curr_img(), mimetype='image/jpg')


@common_prefix.route("/previous-page")
def previous_page():
    controller.previous_page()
    return send_file(controller.get_curr_img(), mimetype='image/jpg')


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

    controller.goto_page(page_idx)

    return send_file(controller.get_curr_img(), mimetype='image/jpg')


# def update_hotkey_map(self, hotkey_map):
#     self.config_manager.update_hotkeys(hotkey_map)
#
#

@common_prefix.route("/load-nav", methods=['POST'])
def load_nav():
    input_file = request.files['doc']
    doc = f"{controller._config_manager.settings.docs_dir}{input_file.filename}"
    input_file.save(doc)
    controller.load_nav(doc)
    # TODO throw errors and implement error handling
    log.debug("saving input doc to %s", doc)
    return 'works'


#
#
# def load_nav(self, doc):
#     self.navigator = self.config_manager.load_existing_nav(doc)
#

# @common_prefix.route("/display-nav-history")
# def display_nav_history():
#     return [nav.to_dict() for nav in config_manager.nav_hist_stack]


#
#
# def teardown(self):
#     self.config_manager.teardown()

app.register_blueprint(common_prefix)
