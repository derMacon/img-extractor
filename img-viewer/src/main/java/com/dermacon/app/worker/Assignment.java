package com.dermacon.app.worker;

import com.dermacon.app.Bookmark;

import java.io.File;

public class Assignment {

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
