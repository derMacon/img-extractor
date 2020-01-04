package com.dermacon.app.worker;

import com.dermacon.app.Bookmark;

import java.io.File;

public class Assignment {

    // QXGA resolution -> needs at least 192 dpi
    // aspect ratio calculator: https://www.aspectratiocalculator.com/4-3.html
    // dpi calculator: http://dpi.lv/
    private static final int DEFAULT_WIDTH = 2048;
    private static final int DEFAULT_HEIGHT = 1536;

    /**
     * Default output resolution of the images (in dots per inch)
     */
    private static int DEFAULT_DPI = 200;

    private final Bookmark bookmark;

    public Assignment(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    public File getCurrImgPath() {
        return new File(bookmark.getImgPath()
                + removeExtension(bookmark.getFile().getName()) + "_"
                + (bookmark.getPageNum() + ".png"));
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public static int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public static int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }

    public static int getDefaultDpi() {
        return DEFAULT_DPI;
    }

    /**
     * Removes the extension from a given full qualified file.
     * @param fullFileName full qualified file name
     * @return name without extension
     */
    protected static String removeExtension(String fullFileName) {
        int idx = fullFileName.lastIndexOf('.');
        if (idx > 0) {
            fullFileName = fullFileName.substring(0, idx);
        }
        return fullFileName;
    }

    public Assignment prev() {
        int prevPageNum = bookmark.getPageNum() - 1;
        Bookmark newBookmark = bookmark.copy();
        newBookmark.setCurrPageIdx(prevPageNum);
        return new Assignment(newBookmark);
    }

    public Assignment next() {
        int nextPageNum = bookmark.getPageNum() + 1;
        Bookmark newBookmark = bookmark.copy();
        newBookmark.setCurrPageIdx(nextPageNum);
        return new Assignment(newBookmark);
    }
}
