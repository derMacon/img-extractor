package com.dermacon.app.dataStructures;

import java.io.File;

public class Bookmark {

    private final File pdf;
    private int currPageIdx;

    // todo builder

    public Bookmark(File pdf, int currPageIdx) {
        this.pdf = pdf;
        this.currPageIdx = currPageIdx;
    }

    public File getFile() {
        return pdf;
    }

    public int getPageNum() {
        return 0;
    }

    public void setCurrPageIdx(int currPageIdx) {
        this.currPageIdx = currPageIdx;
    }


    public Bookmark copy() {
        return new Bookmark(pdf, currPageIdx);
    }

    @Override
    public String toString() {
        return "todo";
    }

}
