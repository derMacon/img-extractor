# from logic.controller import Controller
# from utils.logging_config import log
# 
# controller = Controller()
# controller.load_doc('./test/resources/test-pdf-1.pdf')
# 
# log.debug("nav: %s", controller.next_page())
# log.debug("nav: %s", controller.navigator.to_csv_entry())






from pynput import keyboard

class KeyboardHandler:
    def __init__(self):
        self.current_pressed_keys = set()

    def press_callback(self, key):
        try:
            key = key.char
        except AttributeError:
            key = str(key)
        # self.current_pressed_keys.add(key)
        print("Press event - Current keys:", key)

    def release_callback(self, key):
        try:
            key = key.char
        except AttributeError:
            key = str(key)
        # self.current_pressed_keys.remove(key)
        print("Release event - Current keys:", key)

    def start(self):
        with keyboard.Listener(on_press=self.press_callback, on_release=self.release_callback) as listener:
            listener.join()

if __name__ == "__main__":
    handler = KeyboardHandler()
    handler.start()
