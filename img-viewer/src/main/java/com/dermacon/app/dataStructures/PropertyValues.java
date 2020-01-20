package com.dermacon.app.dataStructures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
 *
 * @author Crunchify.com
 */

public class PropertyValues {

    public static final String NEXT_PAGE = "nextPage";
    public static final String PREV_PAGE = "prevPage";
    public static final String GOTO_PAGE = "gotoPage";
    public static final String IMG_PATH = "imgPath";
    public static final String HISTORY_CSV = "historyCSV";
    public static final String DPI = "dpi";
    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";

    private int height = 0;
    private int width = 0;
    private int dpi = 0;
    private String imgPath = null;
    private File historyCSV = null;
    private int next_command;
    private int prev_command;
    private int goto_command;

    private File propertiesFile;

    public PropertyValues(File file) throws IOException {
        this.propertiesFile = file;
        initPropValues();

    }

    private void initPropValues() throws IOException {

        assert propertiesFile != null;
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();
            inputStream = new FileInputStream(propertiesFile);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propertiesFile.getName() +
                        "' not found in the classpath");
            }

            width = Integer.parseInt(prop.getProperty(WIDTH));
            height = Integer.parseInt(prop.getProperty(HEIGHT));
            dpi = Integer.parseInt(prop.getProperty(DPI));
            prev_command = Integer.parseInt(prop.getProperty(NEXT_PAGE));
            next_command = Integer.parseInt(prop.getProperty(PREV_PAGE));
            goto_command = Integer.parseInt(prop.getProperty(GOTO_PAGE));
            imgPath = prop.getProperty(IMG_PATH);
            historyCSV = new File(prop.getProperty(HISTORY_CSV));

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

    public void setNext_command(int next_command) throws IOException {
        this.next_command = next_command;
        System.out.println("todo update prop file with " + next_command + "\n");
        updatePropertiesFile(PropertyValues.NEXT_PAGE,
                String.valueOf(next_command));

    }

    private void updatePropertiesFile(String key, String value) throws IOException {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            inputStream = new FileInputStream(propertiesFile);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propertiesFile.getName() +
                        "' not found in the classpath");
            }
            System.out.println("old value: " + prop.getProperty(key));
            System.out.println("setting: " + key + ", " + value + ", " + propertiesFile);
            prop.setProperty(key, value);

            try {
                prop.store(new FileOutputStream(propertiesFile.getName()), null);
            } catch (IOException ex) {
                System.out.println(ex);
            }


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

    }

    public void setPrev_command(int prev_command) throws IOException {
        this.prev_command = prev_command;
        System.out.println("todo update prop file with " + prev_command + "\n");
        updatePropertiesFile(PropertyValues.PREV_PAGE,
                String.valueOf(next_command));
    }

    public void setGoto_command(int goto_command) throws IOException {
        this.goto_command = goto_command;
        System.out.println("todo update prop file with " + goto_command + "\n");
        updatePropertiesFile(PropertyValues.GOTO_PAGE,
                String.valueOf(next_command));
    }

    @Override
    public String toString() {
        return "props: {"
                + "\n\theight: " + height
                + "\n\twidth: " + width
                + "\n\tdpi: " + dpi
                + "\n\timgPath: " + imgPath
                + "\n\thistoryCSV: " + historyCSV
                + "\n\tnext_rawCode: " + next_command
                + "\n\tprev_rawCode: " + prev_command
                + "\n\tgoto_rawCode: " + goto_command
                + "\n}";
    }

}