import datetime
import clipboard
from data.settings import *
from utils.logging_config import log
from logic.pdf_converter import PdfConverter
from typing import Set


def now_ts():
    return datetime.datetime.now().timestamp()


class Navigator:

    def __init__(self, doc: str, settings: Settings = None, last_access=now_ts(), curr_page: int = 0):
        self.doc = doc
        self.pdf_converter = PdfConverter(doc, settings)
        self.curr_page_idx = int(curr_page)
        self.curr_page_img = ''
        self._settings = settings
        self.last_access = last_access
        log.debug('created navigator: %s', self.to_csv_entry())

    def filter(self, user_input: Set[str]):
        user_command = self._settings.translate_command_hotkey(user_input)

        if user_command == Command.CLEAN_CLIPBOARD:
            log.debug('cleaning linebreaks from clipboard')
            self.clean_linebreaks_from_clipboard()
            return

        match user_command:
            case Command.NEXT:
                log.debug('next page')
                self.next_page()
            case Command.PREVIOUS:
                log.debug('previous page')
                self.previous_page()
            case _:
                log.debug('not a command')

    def next_page(self):
        if self.curr_page_idx == self.pdf_converter.get_page_count():
            log.info('user tried to navigate out of bound but navigator is already on last page')
        else:
            self.curr_page_idx += 1
            self.curr_page_img = self.pdf_converter.render_img(self.curr_page_idx)

    def previous_page(self):
        if self.curr_page_idx == 0:
            log.info('user tried to navigate out of bound but navigator is already on first page')
        else:
            self.curr_page_idx -= 1
            self.curr_page_img = self.pdf_converter.render_img(self.curr_page_idx)

    def goto_page(self, page_number):
        page_idx = page_number - 1
        if page_idx < 0 or page_idx > self.pdf_converter.get_page_count():
            log.info('user tried to navigate out of bound - goto page %d but doc only contains %d pages', page_idx)
        else:
            self.curr_page_idx = page_idx
            self.curr_page_img = self.pdf_converter.render_img(self.curr_page_idx)

    def clean_linebreaks_from_clipboard(self):
        clipboard_text = clipboard.paste()
        cleaned_text = clipboard_text.replace('\n', ' ').replace('\r', '')  # Replace line breaks with spaces
        clipboard.copy(cleaned_text)
        log.debug("cleaned clipboard content: %s", cleaned_text)

    def __eq__(self, other):
        return other is not None \
            and self.doc == other.doc \
            and self.curr_page_idx == other.curr_page_idx

    def __str__(self):
        return f"nav(doc={self.doc}, page_idx={self.curr_page_idx})"

    def to_dict(self):
        return {
            'doc': self.doc,
            'curr_page_idx': self.curr_page_idx,
            'curr_page_img': self.curr_page_img
        }

    def to_csv_entry(self):
        self.last_access = now_ts()
        return [
            self.doc,
            self.curr_page_idx,
            self.last_access,
        ]
