package com.dermacon.app.dataStructures;

import java.io.File;

public class Bookmark {

    private final File pdf;

    private File currPageImg; // todo
    private int currPageIdx;

    // todo builder

    public Bookmark(File pdf, int currPageIdx) {
        this.pdf = pdf;
        this.currPageIdx = currPageIdx;
    }

    public File getCurrPageImg() {
        return currPageImg;
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

    public void incPageIdx() {
        int pageCnt = 42; // todo
        System.out.println("check / set max page cnt");
        this.currPageIdx++;
    }

    public void decPageIdx() {
        int pageCnt = 42; // todo
        System.out.println("check / set max page cnt");
        this.currPageIdx--;
    }


    public Bookmark copy() {
        return new Bookmark(pdf, currPageIdx);
    }

    @Override
    public String toString() {
        return "Bookmark: \n\tfile: " + this.pdf
                + "\n\tcurrImg: " + this.currPageImg
                + "\n\tpageNum: " + this.currPageIdx;
    }

}
