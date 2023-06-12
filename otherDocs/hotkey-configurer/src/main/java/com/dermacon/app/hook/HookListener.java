package com.dermacon.app.hook;


import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Class that listens for a given shortcut combination and delegates the user input to an appropriate parser.
 */
public class HookListener implements NativeKeyListener {

    //    private static final int PREV_COMMAND = 65478;
//    private static final int NEXT_COMMAND = 65479;
//    private static final int GOTO_COMMAND = 65480;
//
    private static final int ZERO_RAW_CODE = 48;

    private boolean gotoMode = false;
    private StringBuilder pageNum_goto = new StringBuilder();

    public HookListener() {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        // needs to stay empty
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        // needs to stay empty
    }

    /**
     * Similar to vim the user can change into normal mode with a specific key (currently it's the f8 key). Every
     * following number represents a digit of the page num from the pdf file that should be copied to the systems
     * clipboard. The normal mode can be exited by pressing any other kay than a num key.
     *
     * @param event event called by the user and intercepted by the NativeKeyListener in the main class (using this
     *              implementation)
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
//        System.out.println(event.getRawCode());
        int event_rawCode = event.getRawCode();
        System.out.println("event raw code: " + event_rawCode);
//        if (event_rawCode == goto_command || gotoMode) {
//            handleGotoPage(event);
//        } else if (event_rawCode == prev_command) {
//            organizer.prevPage();
//        } else if (event_rawCode == next_command) {
//            organizer.nextPage();
//        }
    }

    /**
     * Checks whether a given event represents a num key on the users keyboard.
     *
     * @param event event called by the user and intercepted by the NativeKeyListener in the main class (using this
     *              implementation)
     * @return true if the key corresponding to the given event represents a number (excluding the numpad).
     */
    private boolean isNum(NativeKeyEvent event) {
        int eventRawCode = event.getRawCode();
        return ZERO_RAW_CODE <= eventRawCode && ZERO_RAW_CODE + 9 >= eventRawCode;
    }

}