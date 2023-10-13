import requests
from typing import Callable
from pynput import keyboard
from src.utils.logging_config import log
from src.logic.settings import Hotkey, Endpoint, Settings


class Keylogger:

    def __init__(self, filter_callback: Callable[[set], None]):
        self.filter_callback = filter_callback
        self.currently_pressed_keys = set()

    @staticmethod
    def _translate_keys(key):
        try:
            key = key.char
        except AttributeError:
            match str(key):
                case 'Key.ctrl':
                    key = 'ctrl'
        return key

    def _press_callback(self, key):
        key = self._translate_keys(key)
        self.currently_pressed_keys.add(key)
        self.filter_callback(self.currently_pressed_keys)

    def _release_callback(self, key):
        key = self._translate_keys(key)
        self.currently_pressed_keys.remove(key)
        log.debug("Release event - Current keys: %s", self.currently_pressed_keys)

    def start(self):
        with keyboard.Listener(on_press=self._press_callback,
                               on_release=self._release_callback) as listener:
            listener.join()

