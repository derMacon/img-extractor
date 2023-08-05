from config_manager import ConfigManager


class Controller:

    def __init__(self, config_manager: ConfigManager, navigator):
        self.config_manager = config_manager
        self.navigator = navigator

    def next_page(self):
        self.navigator.next_page()

    def previous_page(self):
        self.navigator.previous_page()

    def goto_page(self, page_idx):
        self.navigator.goto_page(page_idx)

    def update_hotkey_map(self, hotkey_map):
        self.config_manager.update_hotkeys(hotkey_map)

    def update_doc(self, doc):
        self.config_manager.update_doc(doc)

    def display_nav_history(self):
        return self.config_manager.nav_hist_stack
