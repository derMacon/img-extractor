import os

from src.app.events import *
from src.logic.config_manager import ConfigManager
from src.utils.logging_config import log


@socketio.on('connect')
def handle_connect():
    print('Client connected')
    controller.get_curr_img()


class Controller:
    """Encapsulates the whole logic
    - can be plugged into the api controller
    - can be tested without interfering with the http message passing
    """

    def __init__(self):
        self._config_manager = ConfigManager()
        self._navigator = self._config_manager.load_nav()

    def next_page(self):
        if self._navigator is None:
            log.debug('no active page navigator available')
            raise Exception(f"no active page navigator available - cannot turn to next page")
            # TODO test / handle exception
        else:
            self._navigator.next_page()
            self._config_manager.overwrite_csv()

    def previous_page(self):
        if self._navigator is None:
            log.debug('no active page navigator available')
            raise Exception(f"no active page navigator available - cannot turn to previous page")
            # TODO test / handle exception
        else:
            self._navigator.previous_page()
            self._config_manager.overwrite_csv()

    def goto_page(self, page_idx):
        if self._navigator is None:
            log.debug('no active page navigator available')
            raise Exception(f"no active page navigator available - cannot goto page {page_idx}")
            # TODO test / handle exception
        else:
            log.debug("page_idx: %s", page_idx)
            self._navigator.goto_page(page_idx)
            self._config_manager.overwrite_csv()

    def get_curr_img(self):
        if not self._navigator:
            log.debug('no navigator / doc specified atm - setting placeholder')
            self._navigator = self._config_manager.setup_placeholder()

        path = os.path.abspath(self._navigator.curr_page_img)
        log.debug('get_curr_img: %s', path)
        socketio.start_background_task(send_image, self._navigator.to_dict())

        return path

    def get_nav_stats(self):
        if self._navigator is None:
            return {}
        return self._navigator.to_dict()

    def get_settings(self):
        return self._config_manager.settings

    def update_hotkey_map(self, hotkey_map):
        self._config_manager.update_hotkeys(hotkey_map)

    def load_nav(self, doc):
        self._navigator = self._config_manager.load_nav(doc)

    def display_nav_history(self):
        out = self._config_manager.nav_hist_stack
        # TODO delete logs
        for n in out:
            log.debug('out sorted: %s - %s', n.doc, type(n.last_access))
        out = sorted(out, key=lambda x: x.last_access, reverse=True)
        return out

    def teardown(self):
        self._navigator = self._config_manager.teardown()


controller = Controller()
