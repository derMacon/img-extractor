from enum import Enum
from navigator import Navigator


class Settings:
    class Command(Enum):
        FORWARD = 0
        PREVIOUS = 1

    def __init__(self, ini_path):
        # TODO - parse .ini into object structure
        self.hotkey_map = None
        self.preview_resolution = None
        self.final_resolution = None


class ConfigManager:
    CONFIG_INI_PATH = './todo.ini'

    def __init__(self, keylogger):
        self.keylogger = keylogger
        self.nav_hist_stack = []
        self.settings = Settings(self.CONFIG_INI_PATH)

    def update_doc(self, doc):
        curr_navigator = Navigator(doc, self.settings)
        self.keylogger.observer = curr_navigator
        self.nav_hist_stack.insert(0, curr_navigator)
        # TODO - persist in history .csv
        return curr_navigator

    def update_hotkeys(self, hotkey_map):
        self.settings.hotkey_map = hotkey_map
        # TODO - persist in .ini
        return self.nav_hist_stack[0]
