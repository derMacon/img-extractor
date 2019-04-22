package pdfConverter;

import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        PDFDocument document = new PDFDocument();
        try {
            document.load(new File("input.pdf"));
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }

        SimpleRenderer renderer = new SimpleRenderer();

        // set resolution (in DPI)
        renderer.setResolution(300);

        List<Image> images = null;
        try {
            images = renderer.render(document);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RendererException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < images.size(); i++) {
            try {
                System.out.println(images.get(i));
                ImageIO.write((RenderedImage) images.get(i), "png", new File((i + 1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
