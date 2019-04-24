package logic;

import clipboard.ClipboardImage;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to process the commands given by the user.
 */
public class Organizer {

    private static final int DEFAULT_WIDTH = 930;
    private static final int DEFAULT_HEIGHT = 650;
    /**
     * Default output directory
     */
    public static String OUTPUT_DIR = "out/img/";

    /**
     * Default output resolution of the images (in dots per inch)
     */
    public static int DEFAULT_DPI = 300;

    /**
     * Images representing each page of a given pdf document
     */
    private List<File> images;

    /**
     * Constructor setting the path for the pdf document that will be processed to the list of images.
     * @param doc pdf document to process
     */
    public Organizer(File doc) {
        this.images = new ArrayList<>();
        initOutputDir(OUTPUT_DIR);
        initImages(doc);
    }

    /**
     * Generates a new output directory if the directory with the specified path is non existent
     * @param path path to the output directory
     */
    private void initOutputDir(String path) {
        if(!new File(path).isDirectory()) {
            System.out.println("Created new directory: " + path);
            new File(path).mkdir();
        }
    }

    /**
     * Most important method of this class. It copies a screenshot of the given page number from the specified pdf.
     * @param pageNum number of the page that should be copied to the clipboard
     * @return true if the process ran successful else false
     */
    public boolean copyToClipboard(Integer pageNum) {
        if(null != pageNum && 1 <= pageNum && this.images.size() >= pageNum) {
            System.out.println(this.images.get(pageNum).getName());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            ImageIcon icon = new ImageIcon(this.images.get(pageNum).getPath(), "");

            ClipboardImage clipboardImage = new ClipboardImage(icon.getImage());
            clipboard.setContents(clipboardImage, clipboardImage);

        }
        return true;
    }

    /**
     * Initiates the internal list of images with either a new set of images created from scratch or overrides the
     * current set of images if the number of images doesn't match with the available number of pdf pages.
     * @param doc pdf document to process
     */
    private void initImages(File doc) {
        loadImages();
        if(!imgCorrespondsToDoc(doc)) {
            updatedImgDir(doc);
            loadImages();
        }
    }

    /**
     * Updates the images on the hard drive with new images. Already existent images will not be over written. Only
     * the remaining images which are not included in the image directory will be generated.
     *
     * If images will be over written each time the pdf doc was updated it would take way to long to generate the
     * images every single time.
     *
     * The internal list model will not be initialized in any way -> has own init method
     *
     * @param doc pdf document from which the images will be extracted from
     */
    private void updatedImgDir(File doc) {
        try (final PDDocument document = PDDocument.load(doc)){
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            // todo set starting page num to the file count in the output directory
            for (int page = 0; page < document.getNumberOfPages(); ++page)
            {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DEFAULT_DPI, ImageType.RGB);
                String fileName = OUTPUT_DIR + doc.getName() + "-" + page + ".png";
                ImageIOUtil.writeImage(bim, fileName, DEFAULT_DPI);
//                this.images.add(new File(fileName));
                System.out.println("Printed page " + page);
            }
            document.close();
        } catch (IOException e){
            System.err.println("Exception while trying to create pdf document - " + e);
        }
    }

    /**
     * Loads up all files in the output / image directory into the internal list of images
     */
    private void loadImages() {
        this.images = Arrays.asList(new File(OUTPUT_DIR).listFiles());
        for(File currImg : this.images) {
            ImageResizer.resizeImage(currImg.getPath(), currImg.getPath(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    }
    /**
     * Checks whether or not the internally saved image count corresponds to the page count of the document
     * @return true if the internally saved image count corresponds to the page count of the document
     */
    private boolean imgCorrespondsToDoc(File doc) {
        try {
            return null != doc && doc.isFile()
                    && null != this.images
                    && PDDocument.load(doc).getNumberOfPages() == this.images.size();
        } catch (IOException e) {
            return false;
        }
    }

}
