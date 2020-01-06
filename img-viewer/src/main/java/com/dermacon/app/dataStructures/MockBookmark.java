package com.dermacon.app.dataStructures;

import com.dermacon.app.dataStructures.Bookmark;

import java.io.File;

public class MockBookmark extends Bookmark {

    public MockBookmark() {
        super(new File("test"), 0);
    }
}
