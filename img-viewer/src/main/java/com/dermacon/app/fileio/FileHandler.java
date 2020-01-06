package com.dermacon.app.fileio;

import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.dataStructures.Bookmark;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {

    private static final String PROPERTIES_DEFAULT_PATH = "config.properties";
    private static final String HISTORY_DEFAULT_PATH = "pdf2img_history.csv";
    private static final String HISTORY_HEADING = "pdf,page\n";

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
        props = new PropertyValues(f);
        System.out.println(props);
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

    public void appendHistory(Bookmark bookmark) throws IOException {
        // todo
        File history = props.getHistoryCSV();
        String input = "";
        if (history == null || !history.exists() || history.isDirectory()) {
            history = new File(HISTORY_DEFAULT_PATH);
            input = HISTORY_HEADING;
        }
        input += bookmark.getFile().getPath() + ',' + bookmark.getPageNum();
        FileUtils.writeStringToFile(history, input);
    }

    public Bookmark openNewBookmark() {
        System.out.println("todo open file explorer");
        JFileChooser chooser = new JFileChooser();
        int opt = chooser.showOpenDialog(null);
        File out = null;
        if(opt == JFileChooser.APPROVE_OPTION) {
            out = chooser.getSelectedFile();
            System.out.println("User selected: " + out.getPath());
        }
        return new Bookmark(out, 0);
    }

}
