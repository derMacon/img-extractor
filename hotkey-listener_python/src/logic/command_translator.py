# import keyboard
# import pyperclip
import requests

from src.logic.settings import Settings, Hotkey, Endpoint
from src.utils.logging_config import log


class Translator:

    def __init__(self, settings: Settings):
        self.settings = settings

    def translate_command(self, current_pressed_keys: set[str]):  # TODO set set type as param
        log.debug(f"currently pressed keys {current_pressed_keys}")
        user_command = self.settings.translate_command_hotkey(current_pressed_keys)

        rest_endpoint = None
        match user_command:
            case Hotkey.NEXT:
                log.debug('next page')
                rest_endpoint = self.settings.endpoint_map[Endpoint.NEXT]
            case Hotkey.PREVIOUS:
                log.debug('previous page')
                rest_endpoint = self.settings.endpoint_map[Endpoint.PREVIOUS]
            case Hotkey.CLEAN_CLIPBOARD:
                log.debug('clean clipboard')
                self._clean_clipboard()
            case _:
                pass

        if rest_endpoint is not None:
            try:
                log.debug('calling rest endpoint: %s', rest_endpoint)
                requests.get(rest_endpoint)
            except:
                log.error(f"error calling rest endpoint: {rest_endpoint}")

    def _clean_clipboard(self):
        pass
        # clipboard_text = pyperclip.paste()
        # if isinstance(clipboard_text, str):
        #     text_without_linebreaks = clipboard_text.replace('\n', '').replace('\r', ' ')
        #     pyperclip.copy(text_without_linebreaks)
        #     log.debug("Line breaks removed and copied to clipboard.")
        # else:
        #     log.debug("Clipboard content is not in text form.")

        # clipboard_text = keyboard.read_event().name
        #
        # if isinstance(clipboard_text, str):
        #     text_without_linebreaks = clipboard_text.replace('\n', '').replace('\r', '')
        #     keyboard.write(text_without_linebreaks)
        #
        #     log.debug("Line breaks removed and copied to clipboard.")
        # else:
        #     log.error("Clipboard content is not in text form.")
