package com.dermacon.app.logik;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.fileio.FileHandler;
import com.dermacon.app.logik.configEditor.ConfigListener;
import com.dermacon.app.logik.configEditor.ConfigRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * ui implementation using the terminal
 */
public class TerminalUI implements UserInterface {

//    private static final String TITLE = "PDFToImage-Viewer V2";
//    private static final String TITLE =
//            "  _______   ______     _______   _______   ___   ___ ___   _______ \n" +
//            " |   _   | |   _  \\   |   _   | |       | |   | |   Y   | |   _   |\n" +
//            " |.  1   | |.  |   \\  |.  1___| |___|   | |.  | |.      | |.  |___|\n" +
//            " |.  ____| |.  |    \\ |.  __)    /  ___/  |.  | |. \\_/  | |.  |   |\n" +
//            " |:  |     |:  1    / |:  |     |:  1  \\  |:  | |:  |   | |:  1   |\n" +
//            " |::.|     |::.. . /  |::.|     |::.. . | |::.| |::.|:. | |::.. . |\n" +
//            " `---'     `------'   `---'     `-------' `---' `--- ---' `-------'";

    /**
     * https://devops.datenkollektiv.de/banner.txt/index.html
     * font: "jerusalem"
     */
    private static final String TITLE =
            " ____   ____   _____  ____   ___  __  __   ____ \n" +
                    "|  _ \\ |  _ \\ |  ___||___ \\ |_ _||  \\/  | / ___|\n" +
                    "| |_) || | | || |_     __) | | | | |\\/| || |  _ \n" +
                    "|  __/ | |_| ||  _|   / __/  | | | |  | || |_| |\n" +
                    "|_|    |____/ |_|    |_____||___||_|  |_| \\____|\n";

    private static final String NEW_PDF_COMMAND = "new pdf document";
    private static final String EXIT_COMMAND = "to exit program";
    private static final String CLEAN_COMMAND = "clean temp files / dirs";
    private static final String SET_SHORTCUTS = "set shortcuts";

    private static final String INSTRUCTIONS = TITLE + "\n"
            + "To edit the properties edit the ./config.properties file.\n"
            + "%s\n"
            + "\n"
            + "Type / select: \n"
            + "%s\n"
            + "%s"
            + "%s\n"
            + "Input: ";

    private static final String WARNING = "please try again: ";
    private static final String EXIT_KEYWORD = "exit";
    private static final String EXIT_USAGE = "Type " + EXIT_KEYWORD + " to " +
            "exit program.";

    private static final char DELIMITER_CHAR = '-';

    private FileHandler fileHandler;

    public TerminalUI(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
//        displayOptions(fileHandler.getProps());
    }

    @Override
    public Bookmark waitForUserSelection() {
        return evaluateUserInput();
    }

    /**
     * Displays given property values and displays all the possible commands
     * which the user can call
     *
     * @param props property values distrubuted by the .properties file
     */
    @Override
    public void displayOptions() {
        PropertyValues props = fileHandler.getProps();
        List<String> options = fileHandler.getBookmarks().stream()
                .map(Bookmark::toString)
                .collect(Collectors.toList());

        options.add(0, EXIT_COMMAND);
        options.add(1, NEW_PDF_COMMAND);
        options.add(2, CLEAN_COMMAND);
        options.add(3, SET_SHORTCUTS);
        String strOptions = formatLst(options);

        String delimiter = createDelimiter(options);
        String menu = String.format(INSTRUCTIONS,
                props.toString(), delimiter, strOptions,
                delimiter);

        System.out.print(menu);
    }

    /**
     * Evaluates the user input to a Bookmark
     *
     * @return bookmark which the user selected, null if user executed a
     * command which does not select a bookmark.
     */
    private Bookmark evaluateUserInput() {
        Scanner scanner = new Scanner(System.in);
        Bookmark out = null;
        try {
            out = evaluateInput(scanner.nextLine());
        } catch (InvalidInputException | IOException e) {
            System.out.print(WARNING);
            out = evaluateUserInput();
        }

        return out;
    }

    /**
     * Extracts a bookmark from the given terminal input given by the user.
     *
     * @param in terminal command distributed by the user
     * @return selected Bookmark instance
     * @throws InvalidInputException invalid terminal input (no number in range)
     * @throws IOException           bookmark cannot be created
     */
    private Bookmark evaluateInput(String in) throws InvalidInputException, IOException {
        Integer opt = null;
        Bookmark bookmark = null;
        try {
            opt = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            bookmark = null;
        }

        List<Bookmark> bookmarks = fileHandler.getBookmarks();

        int bm_cnt = bookmark == null ? Integer.MAX_VALUE :
                bookmarks.size();

        if (opt == null || opt < 0 || opt > bm_cnt) {
            throw new InvalidInputException();
        } else {

            switch (opt) {
                case 1:
                    System.exit(0);
                    break;
                case 2:
                    bookmark = fileHandler.openNewBookmark();
                    fileHandler.prependsHistory(bookmark);
//                    bookmark = null;
                    break;
                case 3:
                    System.out.println("clean up");
                    fileHandler.clean();
//                    System.exit(0);
                    bookmark = null;
                    break;
                case 4:
                    System.out.println(SET_SHORTCUTS);
                    changeHotkeys_dialog();
//                    System.exit(0);
                    bookmark = null;
                    break;
                default:
                    bookmark = bookmarks.get(opt - 5);
            }

        }
        return bookmark;
    }

    /**
     * displays the dialog options for the change of the hotkeys. Here the
     * user can specify which keys he wants to map for the following commands:
     * - next page
     * - prev page
     * - goto page
     */
    private void changeHotkeys_dialog() {
        ConfigRunner configRunner =
                new ConfigRunner(this.fileHandler.getProps());
        Thread thr = new Thread(configRunner);
        thr.start();
        try {
            thr.join();
        } catch (InterruptedException e) {
            // todo
            e.printStackTrace();
        }


//        System.out.println("change hotkeys");
//
//        String[] commands = new String[]{"next page", "previous page", "goto" +
//                " command"};
//
//        String currCommand;
//        String userInput;
//        int keyVal;
//
//        Scanner scanner = new Scanner(System.in);
//        for (int i = 0; i < commands.length; i++) {
//            currCommand = commands[i];
//            System.out.print("change " + currCommand + ": ");
//
//            userInput = scanner.nextLine();
//            // todo translate key to NativeKeyEvent
//            keyVal = 42;
//
//            try {
//                switch (i) {
//                    case 0:
//                        fileHandler.getProps().setNext_command(keyVal);
//                        break;
//                    case 1:
//                        fileHandler.getProps().setPrev_command(keyVal);
//                        break;
//                    case 2:
//                        fileHandler.getProps().setGoto_command(keyVal);
//                        break;
//                }
//            } catch(IOException e){
//                System.err.println("could not update properties: " + e.getMessage());
//            }
//        }
    }

    @Override
    public void waitForExit() {
        String userInput = null;
        while (!EXIT_KEYWORD.equals(userInput)) {
            System.out.print(EXIT_USAGE + "\nuser input: ");
            userInput = new Scanner(System.in).next().toLowerCase();
        }
    }

    private static String formatLst(List<String> lst) {
        StringBuilder strb = new StringBuilder();
        Iterator<String> it = lst.iterator();
        int i = 1;
        while (it.hasNext()) {
            strb.append("   " + i++ + ") " + it.next() + "\n");
        }
        return strb.toString();
    }

    private static String createDelimiter(List<String> lst) {
        Integer maxSize = lst.stream().map(String::length)
                .distinct().reduce(Integer::max).get() + 9;

        StringBuilder strb = new StringBuilder();
        for (int i = 0; i < maxSize; i++) {
            strb.append(DELIMITER_CHAR);
        }

        return strb.toString();
    }

}
