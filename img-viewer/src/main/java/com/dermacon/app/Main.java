package com.dermacon.app;

public class Main {


    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(args);
        TerminalUI ui = new TerminalUI(fileHandler.getBookmarks());
        Bookmark selection = ui.waitForUserSelection();

        FxmlApp viewer = new FxmlApp();
        viewer.setBookmark(selection);
        viewer.main(args);
    }

}
