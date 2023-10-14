from src.io.parser_config import config_path
from src.logic.settings import Settings
from src.utils.logging_config import log
from src.logic.keylogger import Keylogger
from src.logic.command_translator import Translator

settings = Settings(config_path)

log.info("configured endpoints: %s", settings.endpoint_map)
log.info("configured shortcuts: %s", settings.hotkey_map)

translator = Translator(settings)
Keylogger(translator.translate_command).start()

# translator._clean_clipboard()