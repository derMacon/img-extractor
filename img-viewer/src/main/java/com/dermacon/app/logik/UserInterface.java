package com.dermacon.app.logik;

import com.dermacon.app.dataStructures.Bookmark;

import java.io.IOException;

public interface UserInterface {
    public Bookmark waitForUserSelection() throws IOException;
    public void waitForExit();
    public void displayOptions();
}
