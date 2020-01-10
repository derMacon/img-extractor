package com.dermacon.app.dataStructures;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class Bookmark {

    private final File pdf;
    private final int pageCnt;

    private int currPageIdx;

    // todo builder

    public Bookmark(File pdf, int currPageIdx) {
        this.pdf = pdf;
        this.currPageIdx = currPageIdx;

        int temp;
        try {
            PDDocument doc = PDDocument.load(pdf);
            temp = doc.getNumberOfPages();
            doc.close();
        } catch (IOException e) {
            temp = Integer.MAX_VALUE;
        }
        this.pageCnt = temp;
    }

    public File getFile() {
        return pdf;
    }

    public int getPageIdx() {
        return currPageIdx;
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
                + "\n\tpageNum: " + this.currPageIdx + "/" + this.pageCnt;
    }

}
