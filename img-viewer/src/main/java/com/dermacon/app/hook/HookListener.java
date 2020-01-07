package com.dermacon.app.hook;


import com.dermacon.app.logik.Organizer;
import com.dermacon.app.worker.Renderer;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Class that listens for a given shortcut combination and delegates the user input to an appropriate parser.
 */
public class HookListener implements NativeKeyListener {

    private static final int PREV_COMMAND = 65478;
    private static final int NEXT_COMMAND = 65479;
    private static final int GOTO_COMMAND = 65480;

    private static final int ZERO_RAW_CODE = 48;
    private static final int NINE_RAW_CODE = 57;

    private boolean normalMode = false;
    private StringBuilder userInput = new StringBuilder();
//    private Organizer organizer;
//    private Renderer renderer;

//    public HookListener(Renderer renderer) {
//        this.renderer = renderer;
//    }

    private Organizer organizer;

    public HookListener(Organizer organizer) {
        this.organizer = organizer;
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
        switch (event.getRawCode()) {
            case PREV_COMMAND:
                System.out.println("prev");
                organizer.prevPage();
                break;
            case NEXT_COMMAND:
                System.out.println("next");
                organizer.nextPage();
            case GOTO_COMMAND:
                System.out.println("goto page");
                // todo
//                Integer pageNum = translateToPageNum(event);
//                boolean successfulCopy = this.organizer.copyToClipboard(pageNum);
//                printState(pageNum, successfulCopy);
        }

    }

    /**
     * Translates a given NativeKeyEvent to the appropriate page number.
     *
     * @param event event triggered by the system wide hook
     * @return Page number that should be copied.
     */
    private Integer translateToPageNum(NativeKeyEvent event) {
        Integer output = null;
        if (normalMode) {
            if (isNum(event)) {
                userInput.append(event.getRawCode() - ZERO_RAW_CODE);
            } else {
                output = Integer.parseInt(this.userInput.toString());
                this.normalMode = false;
                userInput = new StringBuilder();
            }
        } else {
            normalMode = PREV_COMMAND == event.getRawCode();
        }
        return output;
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
        return ZERO_RAW_CODE <= eventRawCode && NINE_RAW_CODE >= eventRawCode;
    }

    /**
     * Prints the current state of the system.
     *
     * @param pageNum           Page number that should be copied to the clipboard
     * @param copiedToClipboard flag determining if the copy process to the clipboard was successful or not.
     */
    private void printState(Integer pageNum, boolean copiedToClipboard) {
        if (null != pageNum && copiedToClipboard) {
            System.out.println("Page " + pageNum + " copied to the clipboard. Puffer cleared.");
        } else if (null != pageNum && !copiedToClipboard) {
            System.out.println("Error: Page " + pageNum + " cannot be copied to clipboard.");
        } else if (normalMode && this.userInput.length() == 0) {
            System.out.println("User entered normal mode");
        } else if (this.normalMode && this.userInput.length() > 0) {
            System.out.println("Current digit puffer: " + this.userInput);
        }
    }

}