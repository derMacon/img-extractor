package com.dermacon.app.worker;

import java.io.File;

public class Assignment {

    private final int pageNum;
    private final File outputDir;

    public Assignment(int pageNum, File outputDir) {
        this.pageNum = pageNum;
        this.outputDir = outputDir;
    }

    public int getPageNum() {
        return pageNum;
    }

    public File getOutputDir() {
        return outputDir;
    }
}
