from pynput import keyboard
from utils.logging_config import log


class Keylogger:

    def __init__(self, observer=None):
        self.observer = observer
        self.current_pressed_keys = set()

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
        self.current_pressed_keys.add(key)
        print("Press event - Current keys:", self.current_pressed_keys)

    def _release_callback(self, key):
        key = self._translate_keys(key)
        self.current_pressed_keys.remove(key)
        print("Release event - Current keys:", self.current_pressed_keys)

    def start(self):
        with keyboard.Listener(on_press=self._press_callback,
                               on_release=self._release_callback) as listener:
            listener.join()
