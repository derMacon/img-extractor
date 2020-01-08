package com.dermacon.app.dataStructures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
 *
 * @author Crunchify.com
 */

public class PropertyValues {

    private int height = 0;
    private int width = 0;
    private int dpi = 0;
    private String imgPath = null;
    private File historyCSV = null;

    public PropertyValues(File file) throws IOException {
        initPropValues(file);
    }

    private void initPropValues(File file) throws IOException {
        assert file != null;
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();
            inputStream = new FileInputStream(file);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + file.getName() +
                        "' not found in the classpath");
            }

            width = Integer.parseInt(prop.getProperty("width"));
            height = Integer.parseInt(prop.getProperty("height"));
            dpi = Integer.parseInt(prop.getProperty("dpi"));
            imgPath = prop.getProperty("imgPath");
            historyCSV = new File(prop.getProperty("historyCSV"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDpi() {
        return dpi;
    }

    public String getImgPath() {
        return imgPath;
    }

    public File getHistoryCSV() {
        return historyCSV;
    }

    @Override
    public String toString() {
        return "props: {height: " + height
                + ", width: " + width
                + ", dpi: " + dpi
                + ", imgPath: " + imgPath
                + ", historyCSV: " + historyCSV + "}";
    }


}