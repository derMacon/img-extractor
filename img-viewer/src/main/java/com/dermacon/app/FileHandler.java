package com.dermacon.app;

import java.util.List;

public class FileHandler {

    private static final String PROPERTIES_DEFAULT_PATH = "./config.properties";

    public List<Bookmark> bookmarks;

    public FileHandler() {
        this(PROPERTIES_DEFAULT_PATH);
    }

    public FileHandler(String... args) {
        this(args != null && args.length == 1 ?
                args[0] : PROPERTIES_DEFAULT_PATH);
    }

    public FileHandler(String propertiesPath) {
        // todo read .properties file and parse bookmark list
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }
}
