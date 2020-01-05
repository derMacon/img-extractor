package com.dermacon.app;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {

    private static final String PROPERTIES_DEFAULT_PATH = "config.properties";

    private List<Bookmark> bookmarks;
    private PropertyValues props;

    public FileHandler() throws IOException {
        this(PROPERTIES_DEFAULT_PATH);
    }

    public FileHandler(String... args) throws IOException {
        this(args != null && args.length == 1 ?
                args[0] : PROPERTIES_DEFAULT_PATH);
    }


    public FileHandler(String propertiesPath) throws IOException {
        // todo read .properties file and parse bookmark list
        File f = new File(propertiesPath);
        if (!f.exists() || f.isDirectory()) {
            f = copyConfigResource();
        }
//        props = new PropertyValues(f);
//        System.out.println(props);
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public PropertyValues getProps() {
        return props;
    }

    private File copyConfigResource() {
        System.out.println("copying default config properties to: "
                + System.getProperty("user.dir"));
        File d = new File(PROPERTIES_DEFAULT_PATH);
        try {
            File r = MyFileUtils.findResourceFile(PROPERTIES_DEFAULT_PATH);
            System.out.println("exists:");
            System.out.println(r.exists());
            FileUtils.copyFile(r, d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public Bookmark openNewFile() {
        // todo
        System.out.println("todo open file explorer");
        return null;
    }

}
