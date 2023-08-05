from config_manager import Settings
from utils.logging_config import log

class Navigator:

    def __init__(self, doc, settings: Settings, curr_page=0):
        self.doc = self.set_doc(doc)
        self.curr_page = curr_page
        self.settings = settings

    def set_doc(self, doc):
        self.doc = doc
        self.max_page = 0 # TODO

    def filter(self, user_input):
        match self.settings.hotkey_map.get(user_input):
            case Settings.Command.FORWARD:
                log.debug('next page')
                self.next_page()
            case Settings.Command.PREVIOUS:
                log.debug('previous page')
                self.next_page()
            case _:
                log.debug('not a command')

    def next_page(self):
        pass

    def previous_page(self):
        pass

    def goto_page(self, page_idx):
        pass

