# import requests
# import multiprocessing
# from pynput import keyboard
# from src.utils.logging_config import log
# from src.data.settings import Command, Settings
#
#
#
# class Keylogger:
#     SERVER_BASE = 'http://localhost:5000/api/v1'
#     ENDPOINT_NEXT_PAGE = SERVER_BASE + '/next-page'
#     ENDPOINT_PREV_PAGE = SERVER_BASE + '/previous-page'
#
#     def __init__(self, settings: Settings):
#         self.settings: Settings = settings
#         self.current_pressed_keys = set()
#
#     @staticmethod
#     def _translate_keys(key):
#         try:
#             key = key.char
#         except AttributeError:
#             match str(key):
#                 case 'Key.ctrl':
#                     key = 'ctrl'
#         return key
#
#     def _press_callback(self, key):
#         key = self._translate_keys(key)
#         self.current_pressed_keys.add(key)
#         log.debug("Press event - Current keys: %s", self.current_pressed_keys)
#         log.debug('type: %s', type(self.settings))
#         user_command = self.settings.translate_command_hotkey(self.current_pressed_keys)
#
#         rest_endpoint = None
#         match user_command:
#             case Command.NEXT:
#                 log.debug('next page')
#                 rest_endpoint = self.ENDPOINT_NEXT_PAGE
#             case Command.PREVIOUS:
#                 log.debug('previous page')
#                 rest_endpoint = self.ENDPOINT_PREV_PAGE
#             case _:
#                 log.debug('not a command')
#
#         if rest_endpoint is not None:
#             log.debug('calling rest endpoint: %s', rest_endpoint)
#             requests.get(rest_endpoint)
#
#     def _release_callback(self, key):
#         key = self._translate_keys(key)
#         self.current_pressed_keys.remove(key)
#         log.debug("Release event - Current keys: %s", self.current_pressed_keys)
#
#     def start(self):
#         with keyboard.Listener(on_press=self._press_callback,
#                                on_release=self._release_callback) as listener:
#             listener.join()
#
#
# class KeyloggerManager:
#     # TODO terminate thread in teardown routine
#
#     def __init__(self):
#         self._proc = None
#
#     def update_settings(self, settings: Settings):
#         log.debug("set up new observer for keylogger: %s", str(settings))
#         # if self._proc is not None:
#         #     self._proc.terminate()
#         # keylogger = Keylogger(settings)
#         # self._proc = multiprocessing.Process(target=keylogger.start, args=())
#         # self._proc.start()
#
#
# keylogger_manager = KeyloggerManager()
