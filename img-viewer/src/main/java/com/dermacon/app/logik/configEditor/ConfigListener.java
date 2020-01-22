package com.dermacon.app.logik.configEditor;

import com.dermacon.app.dataStructures.PropertyValues;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.concurrent.Callable;

public class ConfigListener implements NativeKeyListener {

    private static final String DESCRIPTION = "- set %s: ";

    private static final String[] VALUES_TO_SET = new String[]{
            PropertyValues.NEXT_PAGE,
            PropertyValues.PREV_PAGE,
            PropertyValues.GOTO_PAGE
    };

    private final PropertyValues props;

    private Callable<Boolean> triggerEndProcess;
    private int currValueIdx = 0;

    /**
     * Sets the properties object to edit configs in properties file directly.
     * Sets the trigger to exit listening process when all relevant values
     * have been set.
     *
     * @param props   properties object to edit configs in properties file directly.
     * @param trigger to exit listening process when all relevant values
     *                have been set.
     */
    public ConfigListener(PropertyValues props, Callable<Boolean> trigger) {
        this.props = props;
        this.triggerEndProcess = trigger;
        System.out.print(String.format(DESCRIPTION, VALUES_TO_SET[currValueIdx]));
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        // needs to stay empty
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        // needs to stay empty
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent event) {
        assert currValueIdx >= 0 && currValueIdx < VALUES_TO_SET.length;
        props.setMovementRawCode(VALUES_TO_SET[currValueIdx], event.getRawCode());

        if (currValueIdx >= VALUES_TO_SET.length - 1) {
            try {
                this.triggerEndProcess.call();
            } catch (Exception e) {
                System.out.println("cannot end process: " + e.getMessage());
            }
        } else {
            currValueIdx++;
            System.out.print("\n" + String.format(DESCRIPTION, VALUES_TO_SET[currValueIdx]));
        }
    }
}
