from utils.logging_config import log


def dicts_equal(fst, snd):
    log.debug("fst: %s", fst)
    log.debug("snd: %s", snd)
    if fst is None or snd is None or len(fst) != len(snd):
        log.debug('items None or len does not fit')
        return False

    for (key, val) in fst.items():
        if snd[key] != val:
            log.debug('unequal')
            return False

    return True
