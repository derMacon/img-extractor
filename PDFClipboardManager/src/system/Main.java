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

    private static final String EXIT_KEYWORD = "exit";
    private static final String IMPORT_KEYWORD = "imp";
    private static final String DEFAULT_DIR_FC = "./";
    private static final String USAGE = "usage\n" +
            "\t- EXIT to exit the program\n" +
            "\t- IMP  to import a new pdf file\n";

    private File selectedPdf;

    /**
     * Main method calling the file chooser
     * @param args command line args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startFileChooser(primaryStage);
        System.out.println("PDFClipboardManager: " + this.selectedPdf);

        HookRunner runner = new HookRunner(this.selectedPdf);
        Thread thread = new Thread(runner);
        thread.start();

        Scanner scanner = new Scanner(System.in);
        String userInput = null;
        while (this.selectedPdf != null && !EXIT_KEYWORD.equals(userInput)) {
            System.out.print(USAGE);
            userInput = scanner.next().toLowerCase();
            if(userInput.equals(IMPORT_KEYWORD)) {
                startFileChooser(primaryStage);
            }
        }

        runner.stop();
        thread.join();
        System.out.println("User terminated the programm");
        System.exit(0);
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

