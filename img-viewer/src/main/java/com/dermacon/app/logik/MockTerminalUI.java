package com.dermacon.app.logik;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.fileio.FileHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MockTerminalUI implements UserInterface {

    @Override
    public Bookmark waitForUserSelection() throws IOException {
        return null;
    }

    @Override
    public void waitForExit() {

    }
}
