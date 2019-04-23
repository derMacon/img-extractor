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
import java.util.List;

/**
 * Class to process the commands given by the user.
 */
public class Organizer {

    public static String OUTPUT_DIR = "out/img/";

    public static int DEFAULT_DPI = 300;

    /**
     * Images representing each page of a given pdf document
     */
    private List<File> images;

    /**
     * Constructor setting the path for the pdf document that will be processed to the list of images.
     * @param path path to the pdf document
     */
    public Organizer(String path) {
        this.images = new ArrayList<>();
        initOutputDir(OUTPUT_DIR);
        initImages(path);
    }

    /**
     * Most important method of this class. It copies a screenshot of the given page number from the specified pdf.
     * @param pageNum number of the page that should be copied to the clipboard
     * @return true if the process ran successful else false
     */
    public boolean copyToClipboard(Integer pageNum) {
        if(null != pageNum && 1 <= pageNum && this.images.size() >= pageNum) {
            /* todo implementation
                1. updateImages
                2. getCurrImage
                3. resizeImage
                4. copyImageToClipboard
             */
            System.out.println(this.images.get(pageNum).getName());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            // todo path to img
            ImageIcon icon = new ImageIcon(this.images.get(pageNum).getPath(), "");

            ClipboardImage clipboardImage = new ClipboardImage(icon.getImage());
            clipboard.setContents(clipboardImage, clipboardImage);

        }
        return true;
    }

    private void initImages(String path) {
        File doc = new File(path);
        // todo if directory non existent
        if(!imgCorrespondsToDoc(doc)) {
            overrideImages(doc);
        }
    }

    private void overrideImages(File doc) {
        try (final PDDocument document = PDDocument.load(doc)){
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page)
            {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DEFAULT_DPI, ImageType.RGB);
                String fileName = OUTPUT_DIR + doc.getName() + "-" + page + ".png";
                ImageIOUtil.writeImage(bim, fileName, DEFAULT_DPI);
                this.images.add(new File(fileName));

                System.out.println("Printed page " + page);
            }
            document.close();
        } catch (IOException e){
            System.err.println("Exception while trying to create pdf document - " + e);
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

    private void initOutputDir(String path) {
        // todo implementation
    }
}
