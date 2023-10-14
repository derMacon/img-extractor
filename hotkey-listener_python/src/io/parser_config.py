import argparse
from src.utils.logging_config import log

DEFAULT_CONFIG_PATH = './config.ini'

log.info('starting hotkey listener - press ctrl + c to stop application or stop container environment in which it runs')

_parser = argparse.ArgumentParser(description="hotkey listener, press ctrl + c to terminate application")
_parser.add_argument("--config", "-c", help=f"custom config.ini path, default {DEFAULT_CONFIG_PATH}",
                     default=DEFAULT_CONFIG_PATH)
_args = _parser.parse_args()

config_path = _args.config
log.info(f"user set {config_path} as config path")
