package com.dermacon.app.logik.configEditor;

import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.hook.HookListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigRunner implements Runnable {

    private final PropertyValues props;
    private boolean running = true;

    public ConfigRunner(PropertyValues props) {
        this.props = props;
    }

    @Override
    public void run() {
        try {
            initListener();
        } catch (NativeHookException e) {
            System.out.println("Error: Not possible to create system wide hook");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the native hook listener to make it possible to listen for the key combinations to load up the
     * clipboard with an image.
     */
    private void initListener() throws NativeHookException {
        Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        l.setLevel(Level.OFF);

        GlobalScreen.registerNativeHook();
        Callable<Boolean> trigger_endProcess = () -> {
            this.running = false;
            return this.running;
        };
        ConfigListener hookListener = new ConfigListener(props, trigger_endProcess);
        GlobalScreen.addNativeKeyListener(hookListener);

        while (running) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GlobalScreen.unregisterNativeHook();
    }
}
