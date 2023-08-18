import keyboard
from utils.logging_config import log


class Keylogger:

    def __init__(self, observer=None):
        self.observer = observer
        self.current_pressed_keys = set()

    def _translate_keys(self, event_name):
        if len(event_name) > 1:
            # not a character, special key (e.g ctrl, alt, etc.)
            # uppercase with []
            if event_name == "space":
                # " " instead of "space"
                event_name = " "
            elif event_name == "enter":
                # add a new line whenever an ENTER is pressed
                event_name = "[ENTER]\n"
            elif event_name == "decimal":
                event_name = "."
            else:
                # replace spaces with underscores
                event_name = event_name.replace(" ", "_")
                event_name = f"[{event_name.upper()}]"
        return event_name


    def press_callback(self, event):
        # key = self._translate_keys(event.name)
        # self.current_pressed_keys.add(key)
        print("set: ", self.current_pressed_keys)

        # if not self.observer == None:
        #     print(self.current_pressed_keys)

    def release_callback(self, event):
        print('release called: ', event.name)
        # key = self._translate_keys(event.name)
        # self.current_pressed_keys.remove(key)

    def start(self):
        # pass
        keyboard.on_release(callback=self.release_callback)
        keyboard.on_press(callback=self.press_callback)
        keyboard.wait()
