package com.dermacon.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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

    public PropertyValues(File file) throws IOException {
        getPropValues(file);
    }

    private void getPropValues(File file) throws IOException {
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

//            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            width = Integer.parseInt(prop.getProperty("width"));
            height = Integer.parseInt(prop.getProperty("height"));
            dpi = Integer.parseInt(prop.getProperty("dpi"));

//            result = "Company List = " + company1 + ", " + company2 + ", " + company3;
//            System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
//        return result;
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

    @Override
    public String toString() {
        return "props: {height: " + height
                + ", width: " + width
                + ", dpi: " + dpi + "}";
    }


}