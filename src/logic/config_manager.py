import csv
from logic.navigator import Navigator
from logic.keylogger import Keylogger
from logic.settings import *
from utils.logging_config import log
from utils.io_utils import *


class ConfigManager:
    CONFIG_INI_PATH = './todo.ini'

    def __init__(self, ini_path=CONFIG_INI_PATH):
        self.keylogger = Keylogger()
        self.settings = Settings(self.CONFIG_INI_PATH)
        self.nav_hist_stack = self.parse_history_stack()

    def parse_history_stack(self):
        csv_file = self.settings.history_csv
        log.debug('reading csv file: %s', csv_file)

        if not os.path.exists(csv_file):
            log.debug("csv file at %s does not exist. No navigator data will be loaded", csv_file)
            return []

        out = []
        with open(csv_file, 'r') as file:
            for entry in csv.DictReader(file):
                curr_nav = Navigator(
                    entry[CsvHeader.DOCUMENT.name.lower()],
                    self.settings,
                    entry[CsvHeader.LAST_ACCESSED.name.lower()],
                    entry[CsvHeader.PAGE_IDX.name.lower()],
                )
                out.insert(0, curr_nav)
        out = sorted(out, key=lambda x: x.last_access, reverse=True)
        log.debug('sorted navigation history stack: %s', out)
        return out

    def load_latest_nav(self):
        if not self.nav_hist_stack:
            log.debug('navigation history stack is empty, not able to load latest navigator')
            return None  # empty list
        return self.nav_hist_stack[0]

    def load_existing_nav(self, doc):
        # TODO - improve error handling (don't just return None)

        if not os.path.exists(doc):
            log.error("could not load doc %s", doc)
            return None

        log.debug('load existing nav')
        log.debug('nav_hist_stack: %s', ', '.join(map(str, self.nav_hist_stack)))
        curr_navigator = next((navigator.doc == doc for navigator in self.nav_hist_stack), None)

        if curr_navigator is None:
            log.error("could not load navigator for doc %s from history csv %s", doc, self.settings.history_csv)
            return None

        self.keylogger.observer = curr_navigator
        return curr_navigator

    def create_nav(self, doc):
        log.debug('create navigator for doc: %s', doc)
        curr_navigator = Navigator(doc, self.settings)
        self.keylogger.observer = curr_navigator
        self.nav_hist_stack.insert(0, curr_navigator)

        csv_data = CsvHeader.create_header()
        for navigator in self.nav_hist_stack:
            csv_data.append(navigator.to_csv_entry())

        log.debug('history csv data: %s', csv_data)
        log.debug('history csv file: %s', self.settings.history_csv)

        with open(self.settings.history_csv, "w") as f:
            writer = csv.writer(f)
            for row in csv_data:
                writer.writerow(row)

        return curr_navigator

    def update_hotkeys(self, hotkey_map):
        self.settings.hotkey_map = hotkey_map

        parser = self.settings.config_parser
        ini_section = Command.__name__.lower()

        for (command_name, command_key) in hotkey_map:
            parser.set(ini_section, command_name, command_key)

        return self.nav_hist_stack[0]
    
    def teardown(self):
        log.info('running teardown routine')
        remove_file(self.settings.history_csv)
        remove_file(self.settings.img_dir)

