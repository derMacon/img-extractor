package com.dermacon.app;

import java.io.File;

public class MockBookmark extends Bookmark {

    public MockBookmark() {
        super(new File("test"), new File("test"), 0);
    }
}
