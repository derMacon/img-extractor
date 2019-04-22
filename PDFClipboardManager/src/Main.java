import java.awt.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello world");

        showNames("res/");
        Boolean status = ImageResizer.resizeImage("res/marvinLarge.png", "res/marvinSmall.png",100,100);

    }

    private static void showNames(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}

