import requests
from src.logic.settings import Settings, Hotkey, Endpoint
from src.utils.logging_config import log


class Translator:

    def __init__(self, settings: Settings):
        self.settings = settings

    def tranlate_command(self, current_pressed_keys: set[str]):  # TODO set set type as param
        log.debug("Press event - Current keys: %s", current_pressed_keys)
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
            case _:
                log.debug('not a command')

        if rest_endpoint is not None:
            try:
                log.debug('calling rest endpoint: %s', rest_endpoint)
                requests.get(rest_endpoint)
            except:
                log.error(f"error calling rest endpoint: {rest_endpoint}")
