package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;

import java.io.File;

public class Assignment {

    private final Bookmark bookmark;

    public Assignment(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

//    public File getCurrImgPath() {
//        return new File(bookmark.getImgPath()
//                + removeExtension(bookmark.getFile().getName()) + "_"
//                + (bookmark.getPageNum() + ".png"));
//    }

    public File translateCurrImgPath(String path) {
        return new File(path + File.separator
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
        Bookmark newCopy = bookmark.copy();
        // following function is bound safe, no need to check if copy is
        // valid afterwards
        newCopy.decPageIdx();
        return new Assignment(newCopy);
    }

    public Assignment next() {
        Bookmark newCopy = bookmark.copy();
        // following function is bound safe, no need to check if copy is
        // valid afterwards
        newCopy.incPageIdx();
        return new Assignment(newCopy);
    }
}
