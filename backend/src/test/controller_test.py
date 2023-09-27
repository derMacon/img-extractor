import unittest
# import logging
from logic.controller import Controller
from logic.navigator import Navigator
from data.settings import *

HIST_CSV_FILE = './hist.csv'
SETTINGS_INI_FILE = 'resources/test-config-input.ini'

SAMPLE_DOC_INVALID = './test/res/non-existent.pdf'
SAMPLE_DOC_1 = './test/res/test-pdf-1.pdf'
SAMPLE_DOC_2 = './test/res/test-pdf-2.pdf'
SAMPLE_DOC_3 = './test/res/test-pdf-3.pdf'


class TestControllerMethods(unittest.TestCase):

    def tearDown(self):
        if os.path.exists(HIST_CSV_FILE):
            os.remove(HIST_CSV_FILE)

    def test_test(self):
        self.assertTrue(True)

    def test_navigator_bounds(self):
        controller = Controller()
        controller.create_nav(SAMPLE_DOC_1)

        self.assertEqual(controller._navigator.curr_page_idx, 0)
        self.assertEqual(controller._navigator.doc, SAMPLE_DOC_1)

        controller.next_page()
        self.assertEqual(controller._navigator.curr_page_idx, 1)

        controller.next_page()
        self.assertEqual(controller._navigator.curr_page_idx, 2)

        controller.previous_page()
        self.assertEqual(controller._navigator.curr_page_idx, 1)

        controller.previous_page()
        self.assertEqual(controller._navigator.curr_page_idx, 0)

        controller.previous_page()
        self.assertEqual(controller._navigator.curr_page_idx, 0)

        controller.goto_page(-1)
        self.assertEqual(controller._navigator.curr_page_idx, 0)

        controller.goto_page(477)
        self.assertEqual(controller._navigator.curr_page_idx, 0)

        controller.goto_page(476)
        self.assertEqual(controller._navigator.curr_page_idx, 475)

        controller.previous_page()
        self.assertEqual(controller._navigator.curr_page_idx, 474)

        controller.next_page()
        self.assertEqual(controller._navigator.curr_page_idx, 475)

        controller.next_page()
        self.assertEqual(controller._navigator.curr_page_idx, 475)

        controller.teardown()

    def test_navigator_hotkeys(self):
        controller = Controller()
        controller.create_nav(SAMPLE_DOC_1)
        navigator = controller._navigator

        self.assertEqual(0, navigator.curr_page_idx)
        self.assertEqual(SAMPLE_DOC_1, navigator.doc)

        exp_hotkey_combinations = {
            Command.NEXT: {'ctrl', 'a'},
            Command.PREVIOUS: {'ctrl', 'b'},
            Command.CLEAN_CLIPBOARD: {'ctrl', 'shift', 'c'},
        }

        act_hotkey_combinations = navigator._settings.hotkey_map
        self.assertDictEqual(exp_hotkey_combinations, act_hotkey_combinations)

        navigator.filter({'a', 'ctrl'})
        self.assertEqual(navigator.curr_page_idx, 1)

        navigator.filter({'invalid', 'hotkey'})
        self.assertEqual(navigator.curr_page_idx, 1)

        navigator.filter({'b', 'ctrl'})
        self.assertEqual(navigator.curr_page_idx, 0)

        navigator.filter({'a', 'ctrl'})
        navigator.filter({'ctrl', 'a'})
        self.assertEqual(navigator.curr_page_idx, 2)

        navigator.filter({'b', 'ctrl'})
        navigator.filter({'ctrl', 'b'})
        self.assertEqual(navigator.curr_page_idx, 0)

        navigator.filter({'ctrl', 'b'})
        self.assertEqual(navigator.curr_page_idx, 0)

        controller.teardown()

    def test_history_stack(self):
        self.assertFalse(os.path.exists(HIST_CSV_FILE))

        controller = Controller()
        settings = controller._config_manager.settings
        controller.create_nav(SAMPLE_DOC_1)

        self.assertTrue(os.path.exists(HIST_CSV_FILE))

        controller.next_page()
        act_out = controller._navigator
        exp_out = Navigator(SAMPLE_DOC_1, settings, curr_page=1)
        self.assertEqual(act_out, exp_out)

        controller.create_nav(SAMPLE_DOC_2)
        act_out = controller._navigator
        exp_out = Navigator(SAMPLE_DOC_2, settings)
        self.assertEqual(act_out, exp_out)

        controller.load_nav(SAMPLE_DOC_1)
        act_out = controller._navigator
        exp_out = Navigator(SAMPLE_DOC_1, settings, curr_page=1)
        log.info('act out: %s', act_out)
        log.info('exp out: %s', exp_out)
        self.assertEqual(act_out, exp_out)

        controller.teardown()
        self.assertFalse(os.path.exists(HIST_CSV_FILE))

    def test_settings_ini(self):
        exp_hotkey_combinations = {
            Command.NEXT: {'ctrl', 'a'},
            Command.PREVIOUS: {'ctrl', 'b'},
            Command.CLEAN_CLIPBOARD: {'ctrl', 'shift', 'c'},
        }

        exp_dim_x = 2000
        exp_dim_y = 1000

        exp_history_csv = './hist.csv'
        exp_img_dir = '../img/'

        settings = Settings(SETTINGS_INI_FILE)

        self.assertDictEqual(exp_hotkey_combinations, settings.hotkey_map)

        self.assertTrue(os.path.exists(SETTINGS_INI_FILE))
        self.assertEqual(exp_dim_y, settings.final_resolution[0])
        self.assertEqual(exp_dim_x, settings.final_resolution[1])

        self.assertEqual(exp_history_csv, settings.history_csv)
        self.assertEqual(exp_img_dir, settings.img_dir)

        for (command, hotkey) in exp_hotkey_combinations.items():
            curr_hotkey_input = exp_hotkey_combinations[command]
            curr_act_translate = settings.translate_command_hotkey(curr_hotkey_input)
            self.assertEqual(command, curr_act_translate)

        self.assertEqual(None, settings.translate_command_hotkey({'invalid', 'hotkey'}))


if __name__ == '__main__':
    unittest.main()
