package system;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private final static String EXIT_KEYWORD = "exit";
    private static final String DEFAULT_DIR_FC = "./";

    private static boolean running = true;
    private File selectedPdf;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startFileChooser(primaryStage);
        System.out.println("PDFClipboardManager: " + this.selectedPdf);
        try {
            initListener();
        } catch (NativeHookException e) {
            System.out.println("Error: Not possible to create system wide hook");
            e.printStackTrace();
        }

        // Not working currently -> todo launch initListener in seperate Thread
//        Scanner scanner = new Scanner(System.in);
//        String userInput;
//        do {
//            System.out.print("Type 'exit' to terminate the program: ");
//            userInput = scanner.next();
//        } while(!userInput.equals(EXIT_KEYWORD));
    }

    /**
     * Starts up the initial file chooser to select a pdf document that should be processed
     * @param primaryStage stage to show the file chooser on
     */
    private void startFileChooser(Stage primaryStage) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setInitialDirectory(new File(DEFAULT_DIR_FC));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            System.out.println("User selected file: " + file.getName());
            selectedPdf = file;
        } else {
            System.out.println("User has not selected a valid pdf document");
            System.exit(0);
        }
        primaryStage.setTitle("Choose Pdf-Document");
    }

    /**
     * Initializes the native hook listener to make it possible to listen for the key combinations to load up the
     * clipboard with an image.
     */
    private void initListener() throws NativeHookException {
        Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        l.setLevel(Level.OFF);

        GlobalScreen.registerNativeHook();
        HookListener hookListener = new HookListener(selectedPdf);
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
     * todo delete this method
     *
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

