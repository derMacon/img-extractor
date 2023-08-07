# import keyboard
from utils.logging_config import log


class Keylogger:

    def __init__(self, observer=None):
        self.observer = observer

    # def callback(self, event):
    #     name = event.name
    #     if len(name) > 1:
    #         # not a character, special key (e.g ctrl, alt, etc.)
    #         # uppercase with []
    #         if name == "space":
    #             # " " instead of "space"
    #             name = " "
    #         elif name == "enter":
    #             # add a new line whenever an ENTER is pressed
    #             name = "[ENTER]\n"
    #         elif name == "decimal":
    #             name = "."
    #         else:
    #             # replace spaces with underscores
    #             name = name.replace(" ", "_")
    #             name = f"[{name.upper()}]"
    #     log.debug(name)
    #     if not self.observer == None:
    #         self.observer.filter(name)

    def start(self):
        pass
        # keyboard.on_release(callback=self.callback)
        # keyboard.wait()
