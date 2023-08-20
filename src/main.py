from logic.controller import Controller
from utils.logging_config import log

controller = Controller()
controller.load_nav('./test/resources/test-pdf-1.pdf')

log.debug("nav: %s", controller.next_page())
log.debug("nav: %s", controller.navigator)

# controller.navigator.clean_linebreaks_from_clipboard()
