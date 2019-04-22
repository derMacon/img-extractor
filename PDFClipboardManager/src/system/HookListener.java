package system;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Class that listens for a given shortcut combination and delegates the user input to an appropriate parser.
 */
public class HookListener implements NativeKeyListener {

    private static final int F8_RAW_CODE = 119;
    private static final int ZERO_RAW_CODE = 48;
    private static final int NINE_RAW_CODE = 57;

    private boolean normalMode = false;
    private StringBuilder userInput = new StringBuilder();

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        // needs to stay empty
    }

    /**
     * Similar to vim the user can change into normal mode with a specific key (currently it's the f8 key). Every
     * following number represents a digit of the page num from the pdf file that should be copied to the systems
     * clipboard. The normal mode can be exited by pressing any other kay than a num key.
     * @param event event called by the user and intercepted by the NativeKeyListener in the main class (using this
     *              implementation)
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        if (normalMode) {
            if (isNum(event)) {
                userInput.append(event.getRawCode() - ZERO_RAW_CODE);
            } else {
                this.normalMode = false;
                userInput = new StringBuilder();
            }
        } else {
            normalMode = F8_RAW_CODE == event.getRawCode();
        }
        System.out.println(this.userInput);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        // needs to stay empty
    }

    /**
     * Checks whether a given event represents a num key on the users keyboard.
     * @param event event called by the user and intercepted by the NativeKeyListener in the main class (using this
     *              implementation)
     * @return true if the key corresponding to the given event represents a number (excluding the numpad).
     */
    private boolean isNum(NativeKeyEvent event) {
        int eventRawCode = event.getRawCode();
        return ZERO_RAW_CODE <= eventRawCode && NINE_RAW_CODE >= eventRawCode;
    }
}
