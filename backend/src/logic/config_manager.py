import csv
from src.logic.navigator import *
from src.logic.keylogger import keylogger_manager
from src.data.settings import *
from src.utils.io_utils import *
import shutil


class ConfigManager:

    CONFIG_INI_PATH = './res/runtime/config.ini'
    PLACEHOLDER_PATH = './res/runtime/placeholder.pdf'

    def __init__(self, ini_path=CONFIG_INI_PATH):
        self.keylogger = keylogger_manager
        self.settings = Settings(ini_path)
        self.nav_hist_stack = self.parse_history_stack()
        self._create_tmp_dirs()

    def _create_tmp_dirs(self):
        if not os.path.exists(self.settings.img_dir):
            log.debug("creating img tmp dir: %s", self.settings.img_dir)
            os.makedirs(self.settings.img_dir)
        if not os.path.exists(self.settings.docs_dir):
            log.debug("creating img tmp dir: %s", self.settings.docs_dir)
            os.makedirs(self.settings.docs_dir)

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
                    doc=entry[CsvHeader.DOCUMENT.name.lower()],
                    settings=self.settings,
                    last_access=float(entry[CsvHeader.LAST_ACCESSED.name.lower()]),
                    curr_page=entry[CsvHeader.PAGE_IDX.name.lower()],
                )
                out.insert(0, curr_nav)
        out = sorted(out, key=lambda x: x.last_access, reverse=True)
        log.debug('sorted navigation history stack: %s', out)
        return out

    def load_nav(self, doc=None):
        curr_navigator = None

        if doc is None: # load latest navigator
            if self.nav_hist_stack is None or not self.nav_hist_stack:
                log.debug('navigation history stack is empty, not able to load latest navigator')
                # TODO throw error
            else:
                curr_navigator = self.nav_hist_stack[0]

        # TODO - improve error handling (don't just return None)

        if doc is None or not os.path.exists(doc):
            log.error("doc does not exist %s", doc)
            # TODO exception

        if curr_navigator is None and doc is not None: # existing doc was specified - create nav for it
            log.debug('load existing nav_hist_stack: %s', ', '.join(map(str, self.nav_hist_stack)))
            curr_navigator = next((nav for nav in self.nav_hist_stack if nav.doc == doc), None)
            log.debug('navigator for doc %s is %s', doc, str(curr_navigator))

        if curr_navigator is None and doc is not None:
            log.info("create new navigator - could not load navigator for doc %s from history csv %s", doc,
                      self.settings.history_csv)
            curr_navigator = Navigator(doc, self.settings)
            log.debug("created nav: %s", str(curr_navigator.to_logable()))
            self.nav_hist_stack.insert(0, curr_navigator)

        if curr_navigator:
            log.debug('updating hist.csv with new ts')
            curr_navigator.last_access = now_ts()
            self.overwrite_csv()

        self.keylogger.update_settings(self.settings)
        return curr_navigator


    def overwrite_csv(self):
        csv_data = CsvHeader.create_header()
        for navigator in self.nav_hist_stack:
            csv_data.append(navigator.to_csv_entry())

        log.debug('overwriting history csv file: %s', self.settings.history_csv)

        with open(self.settings.history_csv, "w") as f:
            writer = csv.writer(f)
            for row in csv_data:
                writer.writerow(row)

    def update_hotkeys(self, hotkey_map):
        self.settings.hotkey_map = hotkey_map

        parser = self.settings.config_parser
        ini_section = Command.__name__.lower()

        for (command_name, command_key) in hotkey_map:
            parser.set(ini_section, command_name, command_key)

        return self.nav_hist_stack[0]

    def teardown(self):
        log.info('running teardown routine, deleting history csv and image tmp dir')

        history_csv = os.path.abspath(self.settings.history_csv)
        img_dir = os.path.abspath(self.settings.img_dir)
        docs_dir = os.path.abspath(self.settings.docs_dir)

        remove_file(history_csv)
        remove_file(img_dir)
        remove_file(docs_dir)

        self.nav_hist_stack.clear()
        return self.setup_placeholder()

    def setup_placeholder(self):
        log.debug('setup placeholder')
        self._create_tmp_dirs()

        source_file = self.PLACEHOLDER_PATH
        destination_directory = self.settings.docs_dir
        file_name = os.path.basename(source_file)
        destination_path = os.path.join(destination_directory, file_name)

        try:
            log.debug(f"copying File '{source_file}' to '{destination_path}'.")
            shutil.copy(source_file, destination_path)
        except FileNotFoundError:
            log.erro(f"Source file '{source_file}' not found.")
        except PermissionError:
            log.erro(f"Permission denied. You may not have the necessary permissions to copy the file.")
        except Exception as e:
            log.error(f"An error occurred: {e}")

        return self.load_nav(destination_path)
