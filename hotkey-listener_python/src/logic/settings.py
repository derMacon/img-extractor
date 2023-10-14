import configparser
import json
import os
import sys
from enum import Enum, auto
from typing import Set

from src.utils.logging_config import log


class Endpoint(Enum):
    SERVER_BASE = auto()
    NEXT = auto()
    PREVIOUS = auto()


class Hotkey(Enum):
    NEXT = auto()
    PREVIOUS = auto()
    CLEAN_CLIPBOARD = auto()


class Settings:

    def __init__(self, ini_path):
        if not os.path.exists(ini_path):
            exit_code = 1
            log.error("ini file at %s does not exist. Exiting program with exit code %d", ini_path, exit_code)
            sys.exit(exit_code)

        self.config_parser = configparser.ConfigParser()
        self.config_parser.read(ini_path)

        endpoint_ini_section = self.config_parser[Endpoint.__name__.lower()]
        server_base = json.loads(endpoint_ini_section[Endpoint.SERVER_BASE.name.lower()])

        self.endpoint_map = {
            Endpoint.PREVIOUS: server_base + json.loads(endpoint_ini_section[Endpoint.PREVIOUS.name.lower()]),
            Endpoint.NEXT: server_base + json.loads(endpoint_ini_section[Endpoint.NEXT.name.lower()]),
        }

        command_ini_section = self.config_parser[Hotkey.__name__.lower()]
        self.hotkey_map = {
            Hotkey.NEXT: set(json.loads(command_ini_section[Hotkey.NEXT.name.lower()])),
            Hotkey.PREVIOUS: set(json.loads(command_ini_section[Hotkey.PREVIOUS.name.lower()])),
            Hotkey.CLEAN_CLIPBOARD: set(json.loads(command_ini_section[Hotkey.CLEAN_CLIPBOARD.name.lower()])),
        }

    def translate_command_hotkey(self, hotkeys: Set[str]):
        if hotkeys is None:
            return None

        for (key, val) in self.hotkey_map.items():
            if val == hotkeys:
                return key

        return None
