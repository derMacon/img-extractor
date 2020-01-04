package com.dermacon.app;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TerminalUI {

    private static final String TITLE = "PDFToImage-Viewer V2";
//    private static final String DEFAULT_OPT =

    private static final String DEFAULT_INSTRUCTION = "new pdf document";

    private static final String INSTRUCTIONS = TITLE + "\n" +
            "Type / select: \n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "Input: ";

    private static final String WARNING = "please try again: ";

    private final List<Bookmark> bookmarks;
    private final FileHandler fileHandler;

    public TerminalUI(List<Bookmark> bookmarks, FileHandler fileHandler) {
        this.bookmarks = bookmarks;
        this.fileHandler = fileHandler;
    }


    public Bookmark waitForUserSelection() {
        List<Bookmark> bookmarks = fileHandler.getBookmarks();
        List<String> options = createStrLst(bookmarks);
        options.add(0, DEFAULT_INSTRUCTION);
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

    private Bookmark extractOption(String in) {
        Integer opt = null;
        Bookmark out;
        try {
            opt = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            out = null;
        }

        int bm_cnt = fileHandler.getBookmarks() == null ? Integer.MAX_VALUE :
                fileHandler.getBookmarks().size();
        if (opt == null || opt < 0 || opt > bm_cnt) {
            out = null;
        } else {

            int idx = opt - 1;
            if (idx == 0) {
//            out = fileHandler.openNewFile();
                out = new MockBookmark();
            } else {
//            out = fileHandler.getBookmarks().get(idx);
                out = new MockBookmark();
            }
        }
        return out;
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
    }

    private static String formatLst(List<String> lst) {
        StringBuilder strb = new StringBuilder();
        Iterator<String> it = lst.iterator();
        int i = 1;
        while (it.hasNext()) {
            strb.append("   " + i++ + ") " + it.next());
        }
        return strb.toString();
    }

    private static String createDelimiter(List<String> lst) {
        return "";
    }

}
