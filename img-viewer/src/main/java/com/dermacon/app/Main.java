package com.dermacon.app;

import com.dermacon.app.worker.Manager;
import com.dermacon.app.worker.Renderer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        FileHandler fileHandler = new FileHandler(args);
//        TerminalUI ui = new TerminalUI(fileHandler.getBookmarks(), fileHandler);
//        Bookmark user_select = ui.waitForUserSelection();
//
//        try {
//            Renderer renderer = new Manager(user_select);
//        } catch (IOException e) {
//             todo
//            e.printStackTrace();
//        }
//
        FxmlApp viewer = new FxmlApp();
//        viewer.setBookmark(user_select);
        viewer.main(args);
    }

}
