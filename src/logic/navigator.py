import datetime
from logic.settings import Settings
from utils.logging_config import log


def now_ts():
    return datetime.datetime.now().timestamp()


class Navigator:

    def __init__(self, doc, settings: Settings = None, last_access=now_ts(), curr_page:int = 0):
        self.doc = doc
        self.max_page_idx = 475  # TODO
        self.curr_page_idx = int(curr_page)
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
        if self.curr_page_idx == self.max_page_idx:
            log.info('user tried to navigate out of bound but navigator is already on last page')
        else:
            self.curr_page_idx += 1

    def previous_page(self):
        if self.curr_page_idx == 0:
            log.info('user tried to navigate out of bound but navigator is already on first page')
        else:
            self.curr_page_idx -= 1

    def goto_page(self, page_number):
        page_idx = page_number - 1
        if page_idx < 0 or page_idx > self.max_page_idx:
            log.info('user tried to navigate out of bound - goto page %d but doc only contains %d pages', page_idx)
        else:
            self.curr_page_idx = page_idx

    def to_csv_entry(self):
        self.last_access = now_ts()
        return [
            self.doc,
            self.curr_page_idx,
            self.last_access,
        ]
    
    def __eq__(self, other):
        return other is not None \
            and self.doc == other.doc \
            and self.curr_page_idx == other.curr_page_idx
    
    def __str__(self):
        return f"nav(doc={self.doc}, page_idx={self.curr_page_idx})"
