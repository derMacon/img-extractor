import os
from flask import Flask, jsonify, send_file, Blueprint, request, abort
from flask_cors import cross_origin
from logic.controller import controller
from test.utils.message_parsing_utils import parse_map_input
from utils.logging_config import log

main = Blueprint('main', __name__, url_prefix='/api/v1')


@main.route("/file-history")
@cross_origin()
def show_file_history():
    log.debug('show_file_history')
    out = []
    for nav in controller.display_nav_history():
        filename = os.path.basename(nav.doc)
        out.append(filename)
    log.debug('hist out: %s', out)
    return jsonify(out)


@main.route("/curr-nav")
@cross_origin()
def show_curr_nav():
    return jsonify(controller.get_nav_stats())


@main.route("/settings")
@cross_origin()
def show_settings():
    return jsonify(controller.get_settings().to_dict())


@main.route("/set-hotkey-map")
@cross_origin()
def update_hotkey_map():
    # TODO test this
    hotkey_map = parse_map_input(request)
    controller.update_hotkey_map(hotkey_map)


@main.route("/load-new", methods=['POST'])
@cross_origin()
def load_new():
    input_file = request.files['doc']
    doc = f"{controller.get_settings().docs_dir}{input_file.filename}"
    log.debug("saving input doc to %s", doc)
    input_file.save(doc)
    controller.load_nav(doc)
    # TODO throw errors and implement error handling
    return '', 204  # 204 No Content


@main.route("/load-existing")
@cross_origin()
def load_existing():
    filename = request.args.get('filename')
    # TODO check if arg present & if doc exists- error if not
    log.debug("load existing by filename %s", filename)
    doc = f"{controller.get_settings().docs_dir}{filename}"
    controller.load_nav(doc)
    return send_file(controller.get_curr_img(), mimetype='image/jpg')

# TODO delete this
@main.route("/test-log")
@cross_origin()
def test_log():
    log.debug("test log incommmmmiiiiiinnnnnggg")
    return jsonify('test-log called'), 200


@main.route("/current-page")
@cross_origin()
def current_page():
    return send_file(controller.get_curr_img(), mimetype='image/jpg')


@main.route("/next-page")
@cross_origin()
def next_page():
    controller.next_page()
    return send_file(controller.get_curr_img(), mimetype='image/jpg')


@main.route("/previous-page")
@cross_origin()
def previous_page():
    controller.previous_page()
    return send_file(controller.get_curr_img(), mimetype='image/jpg')


@main.route("/go-to-page")
@cross_origin()
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


@main.route("/teardown")
@cross_origin()
def teardown():
    controller.teardown()
