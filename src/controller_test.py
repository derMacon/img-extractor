import unittest
import os
from logic.controller import Controller
from logic.navigator import Navigator
from utils.logging_config import log

HIST_CSV_FILE = './hist.csv'
SAMPLE_DOC_INVALID = './test/resources/non-existent.pdf'
SAMPLE_DOC_1 = './test/resources/test-pdf-1.pdf'
SAMPLE_DOC_2 = './test/resources/test-pdf-2.pdf'
SAMPLE_DOC_3 = './test/resources/test-pdf-3.pdf'

class TestControllerMethods(unittest.TestCase):

	def tearDown(self):
		if os.path.exists(HIST_CSV_FILE):
			os.remove(HIST_CSV_FILE)

	def test_navigator_bounds(self):
		controller = Controller()
		controller.create_doc(SAMPLE_DOC_1)

		self.assertEqual(controller.navigator.curr_page_idx, 0)
		self.assertEqual(controller.navigator.doc, SAMPLE_DOC_1)

		controller.next_page()
		self.assertEqual(controller.navigator.curr_page_idx, 1)

		controller.next_page()
		self.assertEqual(controller.navigator.curr_page_idx, 2)

		controller.previous_page()
		self.assertEqual(controller.navigator.curr_page_idx, 1)

		controller.previous_page()
		self.assertEqual(controller.navigator.curr_page_idx, 0)

		controller.previous_page()
		self.assertEqual(controller.navigator.curr_page_idx, 0)

		controller.goto_page(-1)
		self.assertEqual(controller.navigator.curr_page_idx, 0)

		controller.goto_page(477)
		self.assertEqual(controller.navigator.curr_page_idx, 0)

		controller.goto_page(476)
		self.assertEqual(controller.navigator.curr_page_idx, 475)

		controller.previous_page()
		self.assertEqual(controller.navigator.curr_page_idx, 474)

		controller.next_page()
		self.assertEqual(controller.navigator.curr_page_idx, 475)

		controller.next_page()
		self.assertEqual(controller.navigator.curr_page_idx, 475)

		controller.teardown()

	
	def test_history_stack(self):
		self.assertFalse(os.path.exists(HIST_CSV_FILE))

		controller = Controller()
		controller.create_doc(SAMPLE_DOC_1)

		self.assertTrue(os.path.exists(HIST_CSV_FILE))

		controller.next_page()
		act_out = controller.navigator
		exp_out = Navigator(SAMPLE_DOC_1, curr_page=1)
		self.assertEqual(act_out, exp_out)

		controller.create_doc(SAMPLE_DOC_2)
		act_out = controller.navigator
		exp_out = Navigator(SAMPLE_DOC_2)
		self.assertEqual(act_out, exp_out)

		controller.load_doc(SAMPLE_DOC_1)
		act_out = controller.navigator
		exp_out = Navigator(SAMPLE_DOC_1, curr_page=1)
		log.info('act out: %s', act_out)
		log.info('exp out: %s', exp_out)
		self.assertEqual(act_out, exp_out)

		controller.teardown()
		self.assertFalse(os.path.exists(HIST_CSV_FILE))


if __name__ == '__main__':
	unittest.main()
