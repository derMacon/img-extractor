import os
from logic.config_manager import ConfigManager
from utils.logging_config import log
from app.events import *


class Controller:
    """Encapsulates the whole logic
    - can be plugged into the api controller
    - can be tested without interfering with the http message passing
    """

    def __init__(self):
        self._config_manager = ConfigManager()
        self._navigator = self._config_manager.load_latest_nav()

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
        path = os.path.abspath(self._navigator.curr_page_img)
        log.debug('get_curr_img: %s', path)
        # send_image(path)
        socketio.start_background_task(send_image, path)
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
        return self._config_manager.nav_hist_stack

    def teardown(self):
        self._config_manager.teardown()


controller = Controller()
