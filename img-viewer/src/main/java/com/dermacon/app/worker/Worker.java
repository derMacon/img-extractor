package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.PropertyValues;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

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
    private final AssignmentStack stack;

    /**
     * Property values distributed by the .config file. Holds information
     * about the rendering size, etc.
     */
    private final PropertyValues props;

    public Worker(AssignmentStack stack, PropertyValues props) {
        this.stack = stack;
        this.props = props;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                render();
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
     * @return an cliboard image which can be saved in the systems clipboard
     * @throws IOException Exception that will be thrown if the selected pdf document cannot be read
     */
    private void render() throws IOException {
        Assignment assignment = stack.getAssignment();
        Bookmark bookmark = assignment.getBookmark();
        System.out.println(Thread.currentThread().getName() + " processes " +
                "page " + bookmark.getPageNum());

        File outputImg = assignment.translateCurrImgPath(props.getImgPath());

        if (outputImg != null && !outputImg.exists()) {
            initOutputDir();
            BufferedImage buffered_img = createBufferedImg(bookmark);

            // write img
            System.out.println("save " + outputImg.getAbsolutePath());
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

        bookmark.setCurrPageImg(outputImg);

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
                pdfRenderer.renderImageWithDPI(bookmark.getPageNum(),
                        props.getDpi(),
                        ImageType.RGB);
        pdf.close();
        return out;
    }
}
