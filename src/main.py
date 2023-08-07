from logic.controller import Controller
from utils.logging_config import log

controller = Controller()
controller.load_doc('./test.pdf')

log.debug("nav: %s", controller.next_page())
log.debug("nav: %s", controller.navigator.to_csv_entry())
