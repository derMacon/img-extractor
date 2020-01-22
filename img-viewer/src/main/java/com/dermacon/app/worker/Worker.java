package com.dermacon.app.worker;

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

    private final static String SMALL_IMG_POSTFIX = "_small";

    /**
     * Assignment stack instance holding a thread safe bookmark stack
     */
    private final MainStack assignments;

    /**
     * Property values distributed by the .config file. Holds information
     * about the rendering size, etc.
     */
    private final PropertyValues props;

    /**
     * Gui controller to set rendered images to.
     */
    private final FXMLController controller;

    // todo builder
    public Worker(MainStack assignments,
                  PropertyValues props,
                  FXMLController controller) {
        this.assignments = assignments;
        this.props = props;
        this.controller = controller;
    }

    /**
     * Pulls new assignments (one at a time) and renders it.
     */
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
     * Renders an image of the given page given that the page is actually
     * existent in the underlying pdf document but only if the page to be
     * rendered in the assignment isn't already up to date. Up to date
     * meaning, the last modified state of the pdf is older than the one from
     * the page image.
     *
     * The page selected by the assignment will be loaded into the systems
     * clipboard and can therefor be directly inserted afterwards by the user.
     *
     * @return an cliboard image which can be saved in the systems clipboard
     * @throws IOException Exception that will be thrown if the selected pdf document cannot be read
     */
    protected void render(Assignment assignment) throws IOException {
        assert assignment != null;
//        System.out.println("thr: " + Thread.currentThread().getName() +
//                "assignment: " + assignment);
        Bookmark bookmark = assignment.getBookmark();
        File outputImg = assignment.translateCurrImgPath();

        if (shouldUpdateImg(outputImg, bookmark.getFile())) {
            initOutputDir();
            BufferedImage buffered_img = createBufferedImg(bookmark);

            // write img
            ImageIOUtil.writeImage(buffered_img,
                    outputImg.getPath(),
                    props.getDisplay_dpi()
            );

            // resize img to ./config.property values
            ImageResizer.resizeImage(
                    outputImg.getPath(),
                    outputImg.getPath(),
                    props.getDisplay_width(),
                    props.getDisplay_height()
            );
        }

        if (this.assignments.shouldDisplay()) {
            controller.updateGui(outputImg, bookmark.getPageIdx() + 1);
            copyToClipboard(outputImg);
        }
    }

    /**
     * Pulls assignment from stack, blocks if stack is empty.
     * @return new assignment to render.
     */
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

    /**
     * Dtermines if the last modified state of the first file is newer than
     * the one from the second one.
     * @param f1 first file to check
     * @param f2 second file to check
     * @return true if the last modified state of the first file is newer than
     * the one from the second one.
     */
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
    public void copyToClipboard(File page) throws IOException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String newPath = page.getPath().replaceAll(".png",
                SMALL_IMG_POSTFIX + ".png");

        File copy = new File(newPath);

        FileUtils.copyFile(page, copy);

        // resize img to ./config.property values
        ImageResizer.resizeImage(
                copy.getPath(),
                copy.getPath(),
                props.getClipboard_width(),
                props.getClipboard_height()
        );

        ImageIcon icon = new ImageIcon(copy.getPath(), "");

        ClipboardImage clipboardImage = new ClipboardImage(icon.getImage());
        clipboard.setContents(clipboardImage, clipboardImage);

    }

    /**
     * Initializes the output directory if is's not already existent.
     * @throws IOException error if the directory cannot be made.
     */
    private void initOutputDir() throws IOException {
        File output_dir = new File(props.getImgPath());
        if (output_dir != null && !output_dir.exists()) {
            FileUtils.forceMkdir(output_dir);
        }
    }

    /**
     * Actual rendering process.
     * Takes a bookmark and generates a bufferedImage from the appropriate
     * page in the bookmark's file.
     *
     * @param bookmark bookmark containing all necessary info regarding the
     *                 place to which the user navigated in an earlier state.
     * @return Image render of the page currently displayed in the bookmark.
     * @throws IOException error if the image cannot be rendered.
     */
    private BufferedImage createBufferedImg(Bookmark bookmark) throws IOException {
        File file = bookmark.getFile();
        PDDocument pdf = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        BufferedImage out =
                pdfRenderer.renderImageWithDPI(bookmark.getPageIdx(),
                        props.getDisplay_dpi(),
                        ImageType.RGB);
        pdf.close();
        return out;
    }
}
