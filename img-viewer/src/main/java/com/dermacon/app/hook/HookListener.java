package com.dermacon.app.hook;


import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.logik.Organizer;
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
//    private static final int NINE_RAW_CODE = 57;

    private boolean gotoMode = false;
    private StringBuilder pageNum_goto = new StringBuilder();

    private final Organizer organizer;
    private final int prev_command;
    private final int next_command;
    private final int goto_command;

    public HookListener(Organizer organizer, PropertyValues props) {
        this.organizer = organizer;
        this.prev_command = props.getPrev_command();
        this.next_command = props.getNext_command();
        this.goto_command = props.getGoto_command();
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
        System.out.println(event.getRawCode());

        int event_rawCode = event.getRawCode();

        if (event_rawCode == goto_command || gotoMode) {
            System.out.println("goto command");
            handleGotoPage(event);
        } else if (event_rawCode == prev_command) {
            System.out.println("prev command");
            organizer.prevPage();
        } else if (event_rawCode == next_command) {
            System.out.println("next command");
            organizer.nextPage();
        }
//        } else {
//            System.out.println("goto page");
//             todo
//                Integer pageNum = translateToPageNum(event);
//                boolean successfulCopy = this.organizer.copyToClipboard(pageNum);
//                printState(pageNum, successfulCopy);
//        }
    }

    /**
     * Translates a given NativeKeyEvent to the appropriate page number and
     * pushes it to the organizer instance.
     *
     * @param event event triggered by the system wide hook
     * @return Page number that should be copied.
     */
    private void handleGotoPage(NativeKeyEvent event) {
        // user already in goto mode
        if (gotoMode) {
            // user types (another) digit from page number
            if (isNum(event)) {
                pageNum_goto.append(event.getRawCode() - ZERO_RAW_CODE);
            } else {
                // user exits goto mode
                gotoMode = false;
                String userInput = pageNum_goto.toString();
                organizer.gotoPage(Integer.parseInt(userInput));
            }
        } else {
            // user enters goto mode
            gotoMode = goto_command == event.getRawCode();
        }
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

    /**
     * Prints the current state of the system.
     *
     * @param pageNum           Page number that should be copied to the clipboard
     * @param copiedToClipboard flag determining if the copy process to the clipboard was successful or not.
     */
    // todo maybe delete this???
    private void printState(Integer pageNum, boolean copiedToClipboard) {
        if (null != pageNum && copiedToClipboard) {
            System.out.println("Page " + pageNum + " copied to the clipboard. Puffer cleared.");
        } else if (null != pageNum && !copiedToClipboard) {
            System.out.println("Error: Page " + pageNum + " cannot be copied to clipboard.");
        } else if (gotoMode && this.pageNum_goto.length() == 0) {
            System.out.println("User entered normal mode");
        } else if (this.gotoMode && this.pageNum_goto.length() > 0) {
            System.out.println("Current digit puffer: " + this.pageNum_goto);
        }
    }

}