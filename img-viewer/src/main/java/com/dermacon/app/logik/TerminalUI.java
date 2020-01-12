package com.dermacon.app.logik;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.MockBookmark;
import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.fileio.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    private final List<Bookmark> bookmarks;

    public TerminalUI(List<Bookmark> bookmarks, PropertyValues props) {
        this.bookmarks = bookmarks;
        displayOptions(props);
    }

    @Override
    public Bookmark waitForUserSelection() {
        return evaluateUserInput();
    }

    private void displayOptions(PropertyValues props) {
        List<String> options = bookmarks.stream()
                .map(Bookmark::toString)
//                .map(File::toString)
                .collect(Collectors.toList());

        options.add(0, EXIT_COMMAND);
        options.add(1, NEW_PDF_COMMAND);
        String strOptions = formatLst(options);

        String delimiter = createDelimiter(options);
        String menu = String.format(INSTRUCTIONS,
                props.toString(), delimiter, strOptions,
                delimiter);

        System.out.print(menu);
    }

    private Bookmark evaluateUserInput() {
        Scanner scanner = new Scanner(System.in);
        Bookmark out = null;
        try {
            out = extractOption(scanner.nextLine());
        } catch (InvalidInputException e) {
            System.out.print(WARNING);
            out = evaluateUserInput();
        }

//        while (correctInput) {
//            System.out.print(WARNING);
//            out = extractOption(scanner.nextLine());
//        }

        return out;
    }

    private Bookmark extractOption(String in) throws InvalidInputException {
        Integer opt = null;
        Bookmark bookmark = null;
        try {
            opt = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            bookmark = null;
        }

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
                    bookmark = null;
                    break;
                default:
                    bookmark = bookmarks.get(opt - 3);
            }

        }
        return bookmark;
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
