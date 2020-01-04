package com.dermacon.app.worker;

import com.dermacon.app.Bookmark;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Worker implements Runnable {

    // QXGA resolution -> needs at least 192 dpi
    // aspect ratio calculator: https://www.aspectratiocalculator.com/4-3.html
    // dpi calculator: http://dpi.lv/
    private static final int DEFAULT_WIDTH = 2048;
    private static final int DEFAULT_HEIGHT = 1536;

    /**
     * Default output resolution of the images (in dots per inch)
     */
    private static int DEFAULT_DPI = 200;

    private final AssignmentStack assignments;
    private final Bookmark bookmark;

    public Worker(AssignmentStack assignments, Bookmark bookmark) {
        this.assignments = assignments;
        this.bookmark = bookmark;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                render(assignments.getAssignment());
                // todo check
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
            } catch (IOException e) {
                System.err.println("image render error: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Renders an image of the given page given that the page is actually existent in the underlying pdf document
     *
     * @param pageNum page num which the user selected
     * @return an cliboard image which can be saved in the systems clipboard
     * @throws IOException Exception that will be thrown if the selected pdf document cannot be read
     */
    public void render(Integer pageNum) throws IOException {
//        System.out.println(Thread.currentThread().getName() + " processes page " + pageNum);
        File file = this.bookmark.getFile();
        PDDocument pdf = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        BufferedImage bim = pdfRenderer.renderImageWithDPI(pageNum - 1, DEFAULT_DPI, ImageType.RGB);
        File currPageImg = bookmark.getCurrImgPath();
        ImageIOUtil.writeImage(bim, currPageImg.getPath(), DEFAULT_DPI);
        ImageResizer.resizeImage(currPageImg.getPath(), currPageImg.getPath(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
