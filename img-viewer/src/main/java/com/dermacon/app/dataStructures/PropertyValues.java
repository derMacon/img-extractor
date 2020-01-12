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
    private int next_command;
    private int prev_command;
    private int goto_command;

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
            prev_command = Integer.parseInt(prop.getProperty("nextPage"));
            next_command = Integer.parseInt(prop.getProperty("prevPage"));
            goto_command = Integer.parseInt(prop.getProperty("gotoPage"));
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

    public int getNext_command() {
        return next_command;
    }

    public int getPrev_command() {
        return prev_command;
    }

    public int getGoto_command() {
        return goto_command;
    }

    @Override
    public String toString() {
        return "props: {height: " + height
                + ", width: " + width
                + ", dpi: " + dpi
                + ", imgPath: " + imgPath
                + ", historyCSV: " + historyCSV
                + ", next_rawCode: " + next_command
                + ", prev_rawCode: " + prev_command
                + ", goto_rawCode: " + goto_command
                + "}";
    }


}