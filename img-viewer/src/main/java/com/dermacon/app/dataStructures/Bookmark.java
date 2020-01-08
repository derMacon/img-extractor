package com.dermacon.app.dataStructures;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class Bookmark {

    private final File pdf;
    private final int pageCnt;

    private File currPageImg; // todo
    private int currPageIdx;

    // todo builder

    public Bookmark(File pdf, int currPageIdx) {
        this.pdf = pdf;
        this.currPageIdx = currPageIdx;

        int temp;
        try {
            temp = PDDocument.load(pdf).getNumberOfPages();
        } catch (IOException e) {
            temp = Integer.MAX_VALUE;
        }
        this.pageCnt = temp;
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
        if (currPageIdx + 1 < pageCnt) {
            currPageIdx++;
            System.out.println("increment page idx to: " + currPageIdx + "/" + pageCnt);
        }
    }

    public void decPageIdx() {
        if (currPageIdx - 1 >= 0) {
            currPageIdx--;
            System.out.println("decrement page idx to: " + currPageIdx + "/" + pageCnt);
        }
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
