from enum import Enum, auto
from utils.logging_config import log
from typing import Set
import json
import os
import sys
import configparser


class Command(Enum):
    NEXT = auto()
    PREVIOUS = auto()
    CLEAN_CLIPBOARD = auto()


class Storage(Enum):
    IMG_DIR = auto()
    DOC_DIR = auto()
    HISTORY_CSV = auto()


class Dimensions(Enum):
    HEIGHT = auto()
    WIDTH = auto()


class CsvHeader(Enum):
    DOCUMENT = 0
    PAGE_IDX = 1
    LAST_ACCESSED = 2

    @classmethod
    def create_header(cls):
        return [[
            CsvHeader.DOCUMENT.name.lower(),
            CsvHeader.PAGE_IDX.name.lower(),
            CsvHeader.LAST_ACCESSED.name.lower(),
        ]]


class Settings:

    def __init__(self, ini_path):
        if not os.path.exists(ini_path):
            exit_code = 1
            log.error("ini file at %s does not exist. Exiting program with exit code %d", ini_path, exit_code)
            sys.exit(exit_code)

        self.config_parser = configparser.ConfigParser()
        self.config_parser.read(ini_path)

        command_ini_section = self.config_parser[Command.__name__.lower()]

        self.hotkey_map = {
            Command.NEXT: set(json.loads(command_ini_section[Command.NEXT.name.lower()])),
            Command.PREVIOUS: set(json.loads(command_ini_section[Command.PREVIOUS.name.lower()])),
            Command.CLEAN_CLIPBOARD: set(json.loads(command_ini_section[Command.CLEAN_CLIPBOARD.name.lower()])),
        }

        self.final_resolution = (
            int(self.config_parser[Dimensions.__name__.lower()][Dimensions.HEIGHT.name.lower()]),
            int(self.config_parser[Dimensions.__name__.lower()][Dimensions.WIDTH.name.lower()]),
        )

        self.history_csv = self.config_parser[Storage.__name__.lower()][Storage.HISTORY_CSV.name.lower()]
        self.img_dir = self.config_parser[Storage.__name__.lower()][Storage.IMG_DIR.name.lower()]
        self.docs_dir = self.config_parser[Storage.__name__.lower()][Storage.DOC_DIR.name.lower()]

    def translate_command_hotkey(self, hotkeys: Set[str]):
        if hotkeys is None:
            return None

        for (key, val) in self.hotkey_map.items():
            if val == hotkeys:
                return key

        return None

    def to_dict(self):
        cleaned_hotkeys = {}

        for (key, val) in self.hotkey_map.items():
            cleaned_hotkeys[key.name] = list(val)

        return {
            'hotkey_map': cleaned_hotkeys,
            'final_resolution': self.final_resolution,
            'history_csv': self.history_csv,
            'img_dir': self.img_dir,
            'docs_dir': self.docs_dir,
        }
