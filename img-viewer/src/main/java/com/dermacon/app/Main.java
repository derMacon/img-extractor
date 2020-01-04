package com.dermacon.app;

import com.dermacon.app.worker.Manager;
import com.dermacon.app.worker.Renderer;

public class Main {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(args);
        TerminalUI ui = new TerminalUI(fileHandler.getBookmarks());
        Bookmark selection = ui.waitForUserSelection();
        Renderer renderer = new Manager();

        FxmlApp viewer = new FxmlApp();
        viewer.setBookmark(selection);
        viewer.main(args);
    }

}
