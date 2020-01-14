package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.AssignmentStack;
import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.ClipboardImage;
import com.dermacon.app.dataStructures.MainStack;
import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.jfx.FXMLController;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Worker class rendering the actual images that will be displayed / copied
 * to the clipboard.
 */
class Worker implements Runnable {

    /**
     * Assignment stack instance holding a thread safe bookmark stack
     */
    private final MainStack assignments;

    /**
     * Property values distributed by the .config file. Holds information
     * about the rendering size, etc.
     */
    private final PropertyValues props;

    private final FXMLController controller;

    // todo builder
    public Worker(MainStack assignments,
                  PropertyValues props,
                  FXMLController controller) {
        this.assignments = assignments;
        this.props = props;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                render(getAssignment());
            } catch (IOException e) {
                System.err.println("image render error: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Renders an image of the given page given that the page is actually existent in the underlying pdf document
     *
     * @return an cliboard image which can be saved in the systems clipboard
     * @throws IOException Exception that will be thrown if the selected pdf document cannot be read
     */
    protected void render(Assignment assignment) throws IOException {
        System.out.println("thr: " + Thread.currentThread().getName() +
                "assignment: " + assignment);

        assert assignment != null;

        Bookmark bookmark = assignment.getBookmark();

        File outputImg = assignment.translateCurrImgPath();

        if (shouldUpdateImg(outputImg, bookmark.getFile())) {
            initOutputDir();
            BufferedImage buffered_img = createBufferedImg(bookmark);

            // write img
            ImageIOUtil.writeImage(buffered_img,
                    outputImg.getPath(),
                    props.getDpi()
            );

            // resize img to ./config.property values
            ImageResizer.resizeImage(
                    outputImg.getPath(),
                    outputImg.getPath(),
                    props.getWidth(),
                    props.getHeight()
            );
        }

        if (assignment.shouldDisplayGui()) {
            System.out.println("assignment: " + assignment);
            controller.updateGui(outputImg, bookmark.getPageIdx() + 1);
            copyToClipboard(outputImg);
        }

    }

    private Assignment getAssignment() {
        Assignment out = assignments.getAssignment();

        if (out == null) {
            assignments.blockThread();
            out = getAssignment();
        }

        return out;
    }

    /**
     * Determines if the image with the given path should be rendered or not.
     * When the file is not existent it should be rendered.
     * When the file is already rendered but older than the linked bookmark
     * file than it also should be rendered since the pdf can be updated.
     *
     * @param img
     * @return
     */
    private static boolean shouldUpdateImg(File img, File pdf) {
        return !img.exists() || fstFileNewer(pdf, img);
    }

    private static boolean fstFileNewer(File f1, File f2) {
        long d1 = f1.lastModified();
        long d2 = f2.lastModified();

        return d1 == d2 || d1 > d2;
    }

    /**
     * Copies a screenshot of the given page number from the specified pdf.
     *
     * @return true if the process ran successful else false
     */
    public void copyToClipboard(File page) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        ImageIcon icon = new ImageIcon(page.getPath(), "");

        ClipboardImage clipboardImage = new ClipboardImage(icon.getImage());
        clipboard.setContents(clipboardImage, clipboardImage);

    }

    private void initOutputDir() throws IOException {
        File output_dir = new File(props.getImgPath());
        if (output_dir != null && !output_dir.exists()) {
            FileUtils.forceMkdir(output_dir);
        }
    }

    private BufferedImage createBufferedImg(Bookmark bookmark) throws IOException {
        File file = bookmark.getFile();
        PDDocument pdf = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        BufferedImage out =
                pdfRenderer.renderImageWithDPI(bookmark.getPageIdx(),
                        props.getDpi(),
                        ImageType.RGB);
        pdf.close();
        return out;
    }
}
