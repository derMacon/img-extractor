package com.dermacon.app.worker;

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

    private final Integer pageNum;
    private final Integer pageCnt;
    private final File pdf;
    private final File outputDir;

    public static class AssignmentBuilder {

        private Integer pageNum;
        private Integer pageCnt;
        private File pdf;
        private File outputDir;

        public AssignmentBuilder setPageNum(Integer pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public AssignmentBuilder setPageCnt(Integer pageCnt) {
            this.pageCnt = pageCnt;
            return this;
        }

        public AssignmentBuilder setPdf(File pdf) {
            this.pdf = pdf;
            return this;
        }

        public AssignmentBuilder setOutputDir(File outputDir) {
            this.outputDir = outputDir;
            return this;
        }

        public Assignment build() {
            return new Assignment(this);
        }

    }

    private Assignment(AssignmentBuilder builder) {
        pageNum = builder.pageNum;
        pageCnt = builder.pageCnt;
        pdf = builder.pdf;
        outputDir = builder.outputDir;
        assert pageNum != null;
        assert pageCnt != null;
        assert pdf != null;
        assert outputDir != null;
    }

    public int getPageNum() {
        return pageNum;
    }

    public File getFile() {
        return pdf;
    }

    public File getOutputDir() {
        return outputDir;
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
     * Creates a new Bookmark assignment with the succeeding page
     * @return Assignment that will be rendered, null if the next page is out
     * of bound
     */
    public Assignment next() {
        int nextPageNum = pageNum + 1;
        if (nextPageNum > pageCnt) {
            return null;
        }
        return new AssignmentBuilder().setPageNum(nextPageNum)
                .setPageCnt(pageCnt)
                .setPdf(pdf)
                .setOutputDir(outputDir)
                .build();
    }

    /**
     * See next-method javadoc
     * @return
     */
    public Assignment prev() {
        int nextPageNum = pageNum - 1;
        if (nextPageNum < 1) {
            return null;
        }
        return new AssignmentBuilder().setPageNum(nextPageNum)
                .setPageCnt(pageCnt)
                .setPdf(pdf)
                .setOutputDir(outputDir)
                .build();
    }
}
