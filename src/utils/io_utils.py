import os
from utils.logging_config import log

def remove_file(file):
	try:
		os.remove(file)
		log.info('successfully removed %s', file)
	except OSError as e:
		log.error('teardown incomplete - error removing %s', file)