package com.dermacon.app;

import java.io.File;

public class Bookmark {

    private final File pdf;
    private final File imgPath;
    private final int currPageIdx;

    public Bookmark(File pdf, File imgPath, int currPageIdx) {
        this.pdf = pdf;
        this.imgPath = imgPath;
        this.currPageIdx = currPageIdx;
    }

    public File getFile() {
        return pdf;
    }

    public File getCurrImgPath() {
        return new File(imgPath + removeExtension(pdf.getName()) + "_"
                + (currPageIdx + 1) + ".png");
    }

    public int getPageNum() {
        return 0;
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

    @Override
    public String toString() {
        return "todo";
    }

}
