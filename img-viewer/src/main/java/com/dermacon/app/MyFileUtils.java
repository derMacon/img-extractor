package com.dermacon.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Class to find the appropriate resource file
 *
 * template source: https://www.rgagnon.com/javadetails/java-0665.html
 */
public class MyFileUtils {

    public static File findResourceFile(String fileName) {
//        try {
//            main(new String[0]);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        List<File> list = MyFileUtils.getFiles(System.getProperty("java.class.path"));
        System.out.println("file lst: ");
        System.out.println(list);
        Iterator<File> it = list.iterator();
        File out = null;
        File temp = null;
        while (out == null && it.hasNext()) {
            temp = it.next();
            if (temp.getName().equals(fileName)) {
                out = temp;
            }
        }
        return out;
    }



    /**
     * list files in the given directory and subdirs (with recursion)
     * @param paths
     * @return
     */
    private static List<File> getFiles(String paths) {
        List<File> filesList = new ArrayList<File>();
        for (final String path : paths.split(File.pathSeparator)) {
            final File file = new File(path);
            if( file.isDirectory()) {
                recurse(filesList, file);
            }
            else {
                filesList.add(file);
            }
        }
        return filesList;
    }

    private static void recurse(List<File> filesList, File f) {
        File list[] = f.listFiles();
        for (File file : list) {
            if (file.isDirectory()) {
                recurse(filesList, file);
            }
            else {
                filesList.add(file);
            }
        }
    }

//    /**
//     * List the content of the given jar
//     * @param jarPath
//     * @return
//     * @throws IOException
//     */
//    public static List<String> getJarContent(String jarPath) throws IOException {
//        List<String> content = new ArrayList<String>();
//        JarFile jarFile = new JarFile(jarPath);
//        Enumeration<JarEntry> e = jarFile.entries();
//        while (e.hasMoreElements()) {
//            JarEntry entry = (JarEntry)e.nextElement();
//            String name = entry.getName();
//            content.add(name);
//        }
//        return content;
//    }
//
//    public static void main(String args[]) throws Exception {
//        List<File> list = MyFileUtils.getFiles(System.getProperty("java.class" +
//                ".path"));
//        for (File file: list) {
//            System.out.println(file.getPath());
//        }
//
//        list = MyFileUtils.getFiles(System.getProperty("sun.boot.class.path"));
//        for (File file: list) {
//            System.out.println(file.getPath());
//        }
//        list = MyFileUtils.getFiles(System.getProperty("java.ext.dirs"));
//        for (File file: list) {
//            System.out.println(file.getPath());
//        }
//
//        List<String> content = MyFileUtils.getJarContent("c:/temp/DirWatch" +
//                ".jar");
//        for (String file: content) {
//            System.out.println(file);
//        }
//
//    }

}