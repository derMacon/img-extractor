from enum import Enum, auto
from utils.logging_config import log
import os
import sys
import configparser


class Command(Enum):
    NEXT = auto()
    PREVIOUS = auto()


class Storage(Enum):
    IMG_DIR = auto()
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

        self.hotkey_map = {
            self.config_parser[Command.__name__.lower()][Command.NEXT.name.lower()], Command.NEXT,
            self.config_parser[Command.__name__.lower()][Command.PREVIOUS.name.lower()], Command.PREVIOUS,
        }

        self.final_resolution = (
            self.config_parser[Dimensions.__name__.lower()][Dimensions.HEIGHT.name.lower()], Dimensions.HEIGHT,
            self.config_parser[Dimensions.__name__.lower()][Dimensions.WIDTH.name.lower()], Dimensions.WIDTH,
        )

        self.history_csv = self.config_parser[Storage.__name__.lower()][Storage.HISTORY_CSV.name.lower()]
        self.img_dir = self.config_parser[Storage.__name__.lower()][Storage.IMG_DIR.name.lower()]
