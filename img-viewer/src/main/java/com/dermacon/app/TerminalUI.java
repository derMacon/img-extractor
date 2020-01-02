package com.dermacon.app;

import java.io.File;
import java.util.List;

public class TerminalUI {

    private static final String TITLE = "PDFToImage-Viewer v2";
//    private static final String DEFAULT_OPT =

    private static final String INSTRUCTIONS = TITLE + "\n" +
            "Type / select: \n" +
            "  - 0) new pdf document\n" +
            "%s\n%s\n" +
            "Input: ";

    private final List<Bookmark> bookmarks;

    public TerminalUI(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }


    public Bookmark waitForUserSelection() {
//        String delimiter = createDelimiter();
//        String options = formatList(fileHandler.getBookmarks());
//        String menu = String.format(INSTRUCTIONS, )));

//        System.out.print(menu);

        return new Bookmark();
    }

    private static <E> String formatList(List<E> lst) {
        if (lst == null) {
            return "";
        }
        StringBuilder strb = new StringBuilder();
        for (E e : lst) {
            strb.append(e);
        }
        return strb.toString();
    }

    private static String createDelimiter() {
        return "";
    }

}
