package com.dermacon.app.logik;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.MockBookmark;
import com.dermacon.app.fileio.FileHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TerminalUI implements UserInterface {

    private static final String TITLE = "PDFToImage-Viewer V2";

    private static final String NEW_PDF_COMMAND = "new pdf document";
    private static final String EXIT_COMMAND = "to exit program";

    private static final String INSTRUCTIONS = TITLE + "\n"
            + "To edit the properties edit the ./config.properties file.\n"
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
    private final FileHandler fileHandler;

    public TerminalUI(List<Bookmark> bookmarks, FileHandler fileHandler) {
        this.bookmarks = bookmarks;
        this.fileHandler = fileHandler;
    }

    @Override
    public Bookmark waitForUserSelection() throws IOException {
        List<Bookmark> bookmarks = fileHandler.getBookmarks();
        List<String> options = createStrLst(bookmarks);
        options.add(0, EXIT_COMMAND);
        options.add(1, NEW_PDF_COMMAND);
        String strOptions = formatLst(options);

        String delimiter = createDelimiter(options);
        String menu = String.format(INSTRUCTIONS, delimiter, strOptions, delimiter);

        System.out.print(menu);
        Scanner scanner = new Scanner(System.in);
        Bookmark out = extractOption(scanner.nextLine());

        while (out == null) {
            System.out.print(WARNING);
            out = extractOption(scanner.nextLine());
        }

        return out;
    }

    private Bookmark extractOption(String in) throws IOException {
        Integer opt = null;
        Bookmark bookmark = null;
        try {
            opt = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            bookmark = null;
        }

        int bm_cnt = fileHandler.getBookmarks() == null ? Integer.MAX_VALUE :
                fileHandler.getBookmarks().size();

        if (opt == null || opt < 0 || opt > bm_cnt) {
            bookmark = null;
        } else {

            switch (opt) {
                case 1:
                    System.exit(0);
                    break;
                case 2:
                    bookmark = fileHandler.openNewBookmark();
                    fileHandler.appendHistory(bookmark);
//                    out = new MockBookmark();
                    break;
                default:
//            out = fileHandler.getBookmarks().get(idx - 1);
                    bookmark = new MockBookmark();
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

    // todo stream
    private static <E> List<String> createStrLst(List<E> lst) {
        List<String> out = new LinkedList<>();
        if (lst != null) {
            for (E e : lst) {
                out.add(e.toString());
            }
        }
        return out;
//        return lst.stream().map(Object::toString).collect(Collectors.toList());
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
