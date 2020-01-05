package com.dermacon.app;

import java.io.File;

public class Bookmark {

    private final File pdf;
    private final File imgPath;
    private int currPageIdx;

    // todo builder

    public Bookmark(File pdf, File imgPath, int currPageIdx) {
        this.pdf = pdf;
        this.imgPath = imgPath;
        this.currPageIdx = currPageIdx;
    }

    public File getFile() {
        return pdf;
    }

    public File getImgPath() {
        return imgPath;
    }

    public int getPageNum() {
        return 0;
    }

    public void setCurrPageIdx(int currPageIdx) {
        this.currPageIdx = currPageIdx;
    }


    public Bookmark copy() {
        return new Bookmark(pdf, imgPath, currPageIdx);
    }

    @Override
    public String toString() {
        return "todo";
    }

}
