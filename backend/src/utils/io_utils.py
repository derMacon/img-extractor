import os
import shutil

from src.utils.logging_config import log


def remove_file(path):
    try:
        if os.path.isfile(path):
            os.remove(path)
            log.info('successfully removed file %s', path)
            return

        if os.path.isdir(path):
            shutil.rmtree(path)
            log.info('successfully removed dir %s', path)
            return

        log.error('teardown incomplete - error removing %s', path)

    except OSError as e:
        log.error('teardown incomplete - error removing %s', path)
        log.error('%s', e)
