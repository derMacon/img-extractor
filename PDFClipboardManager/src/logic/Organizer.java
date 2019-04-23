package logic;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;

/**
 * Class to process the commands given by the user.
 */
public class Organizer {

    /**
     * Images representing each page of a given pdf document
     */
    private File[] images;

    /**
     * Constructor setting the path for the pdf document that will be processed to the list of images.
     * @param path path to the pdf document
     */
    public Organizer(String path) {
        // todo init images
    }

    /**
     * Most important method of this class. It copies a screenshot of the given page number from the specified pdf.
     * @param pageNum number of the page that should be copied to the clipboard
     * @return true if the process ran successful else false
     */
    public boolean copyToClipboard(Integer pageNum) {
        if(null != pageNum) {
            /* todo implementation
                1. updateImages
                2. getCurrImage
                3. resizeImage
                4. copyImageToClipboard
             */
        }
        return true;
    }

    private void initImages(String path) {
        File doc = new File(path);
        if(!imgCorrespondsToDoc(doc)) {

        }
    }

    /**
     * Checks whether or not the internally saved image count corresponds to the page count of the document
     * @return true if the internally saved image count corresponds to the page count of the document
     */
    private boolean imgCorrespondsToDoc(File doc) {
        try {
            return null != doc && doc.isFile()
                    && PDDocument.load(doc).getNumberOfPages() == this.images.length;
        } catch (IOException e) {
            return false;
        }
    }
}
