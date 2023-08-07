import datetime
from logic.settings import Settings
from utils.logging_config import log


def now_ts():
    return datetime.datetime.now().timestamp()


class Navigator:

    def __init__(self, doc, settings: Settings, last_access=now_ts(), curr_page=0):
        self.doc = doc
        self.max_page = 0  # TODO
        self.curr_page_idx = curr_page
        self.settings = settings
        self.last_access = last_access
        log.debug('created navigator: %s', self.to_csv_entry())

    def filter(self, user_input):
        match self.settings.hotkey_map.get(user_input):
            case Settings.Command.NEXT:
                log.debug('next page')
                self.next_page()
            case Settings.Command.PREVIOUS:
                log.debug('previous page')
                self.next_page()
            case _:
                log.debug('not a command')

    def next_page(self):
        self.curr_page_idx += 1

    def previous_page(self):
        self.curr_page_idx -= 1

    def goto_page(self, page_idx):
        self.curr_page_idx = page_idx

    def to_csv_entry(self):
        self.last_access = now_ts()
        return [
            self.doc,
            self.curr_page_idx,
            self.last_access,
        ]
