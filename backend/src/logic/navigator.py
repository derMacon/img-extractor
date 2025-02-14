import base64
import datetime
import os

import cv2

# import clipboard
from src.data.settings import *
from src.logic.pdf_converter import PdfConverter
from src.utils.logging_config import log


def now_ts():
    return datetime.datetime.now().timestamp()


class Navigator:

    def __init__(self, doc: str, settings: Settings = None, last_access: float = now_ts(), curr_page: int = 0):
        self.doc = doc
        self.pdf_converter = PdfConverter(doc, settings)
        self.curr_page_idx = int(curr_page)
        self.curr_page_img = self.pdf_converter.render_img(self.curr_page_idx)
        self._settings = settings
        self.last_access = last_access
        log.debug('created navigator: %s', self.to_csv_entry())

    def filter(self, user_input: Set[str]):
        user_command = self._settings.translate_command_hotkey(user_input)

        # if user_command == Command.CLEAN_CLIPBOARD:
        #     log.debug('cleaning linebreaks from clipboard')
        #     self.clean_linebreaks_from_clipboard()
        #     return

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

    def __eq__(self, other):
        return other is not None \
            and self.doc == other.doc \
            and self.curr_page_idx == other.curr_page_idx

    def __str__(self):
        return f"nav(doc={self.doc}, page_idx={self.curr_page_idx})"

    def to_dict(self):
        frame = cv2.imread(self.curr_page_img)
        filename = os.path.basename(self.doc)
        image_data = None
        if frame is not None:
            _, img_encoded = cv2.imencode('.jpg', frame)
            image_data = base64.b64encode(img_encoded).decode('utf-8')

        return {
            'doc': filename,
            'page_cnt': self.pdf_converter.get_page_count(),
            'curr_page_idx': self.curr_page_idx,
            'curr_page_img': image_data,
        }

    def to_logable(self):
        return {
            'doc': self.doc,
            'page_cnt': self.pdf_converter.get_page_count(),
            'curr_page_idx': self.curr_page_idx,
        }

    def to_csv_entry(self):
        return [
            self.doc,
            self.curr_page_idx,
            self.last_access,
        ]
