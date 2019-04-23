package system;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static boolean running = true;

    public static void main(String[] args) {
        System.out.println("PDFClipboardManager");
        try {
            initListener("res/doc.pdf");
        } catch (NativeHookException e) {
            System.out.println("Error: Not possible to create system wide hook");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the native hook listener to make it possible to listen for the key combinations to load up the
     * clipboard with an image.
     */
    private static void initListener(String path) throws NativeHookException {
        Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        l.setLevel(Level.OFF);

        GlobalScreen.registerNativeHook();
        HookListener hookListener = new HookListener(path);
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

    /**
     * testing method to see files in the directory
     * @param path path of the directory to show
     */
    private static void showNames(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}

