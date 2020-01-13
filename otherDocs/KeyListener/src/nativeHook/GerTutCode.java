package nativeHook;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tut irgendwie nix :(
 */
public class GerTutCode {
    private static boolean running = true;

    public static void main(String[] args) throws NativeHookException {
        Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        l.setLevel(Level.OFF);

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(listener);

        while(running) {
            try {
                System.out.println("bis hier");
                Thread.sleep(300);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        GlobalScreen.unregisterNativeHook();
    }

    private static NativeKeyListener listener = new NativeKeyListener() {
        @Override
        public void nativeKeyTyped(NativeKeyEvent e) {
            System.out.println("Key pressed: "+ NativeKeyEvent.getKeyText(e.getKeyCode()));
            if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                running = false;
            }
        }

        @Override
        public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

        }
    };

}
