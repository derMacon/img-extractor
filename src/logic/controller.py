from utils.logging_config import log
from logic.config_manager import ConfigManager


class Controller:

    def __init__(self):
        self.config_manager = ConfigManager()
        self.navigator = self.config_manager.load_latest_nav()

    def next_page(self):
        if self.navigator is None:
            log.debug('no active page navigator available')
        else:
            self.navigator.next_page()

    def previous_page(self):
        if self.navigator is None:
            log.debug('no active page navigator available')
        else:
            self.navigator.previous_page()

    def goto_page(self, page_idx):
        if self.navigator is None:
            log.debug('no active page navigator available')
        else:
            self.navigator.goto_page(page_idx)

    def update_hotkey_map(self, hotkey_map):
        self.config_manager.update_hotkeys(hotkey_map)

    def create_nav(self, doc):
        self.navigator = self.config_manager.create_nav(doc)

    def load_nav(self, doc):
        self.navigator = self.config_manager.load_existing_nav(doc)

    def display_nav_history(self):
        return self.config_manager.nav_hist_stack

    def teardown(self):
        self.config_manager.teardown()
