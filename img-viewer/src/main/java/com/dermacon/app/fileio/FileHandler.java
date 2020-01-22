package com.dermacon.app.fileio;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.PropertyValues;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileHandler {

    private static final String PROPERTIES_DEFAULT_NAME = "config.properties";
    private static final String PROPERTIES_RESOURCE_PATH =
            "com/dermacon/app/" + PROPERTIES_DEFAULT_NAME;
    private static final String HISTORY_DEFAULT_PATH = "pdf2img_history.csv";
    private static final String HISTORY_HEADING = "pdf,page";

    private static final String PDF_CSV_CAPTION = "pdf";
    private static final String PAGENUM_CSV_CAPTION = "page";


    private List<Bookmark> bookmarks = new LinkedList<>();
    private PropertyValues props;

    public FileHandler() throws IOException, CSVException {
        this(PROPERTIES_DEFAULT_NAME);
    }

    public FileHandler(String... args) throws IOException, CSVException {
        this(args != null && args.length == 1 ?
                args[0] : PROPERTIES_DEFAULT_NAME);
    }


    public FileHandler(String propertiesPath) throws IOException, CSVException {
        File f = new File(propertiesPath);
        if (!f.exists() || f.isDirectory()) {
            f = copyResourceConfig();
        }
        props = new PropertyValues(f);

        initBookmarks();
    }

    public void initBookmarks() throws IOException, CSVException {
        File file = props.getHistoryCSV();
        if (file != null && file.exists()) {
            Map<String, List<String>> map = CSVReader.readCSV(props.getHistoryCSV());

            if (map == null || map.size() != 2) {
                throw new CSVException("csv file - wrong column names, please " +
                        "delete csv file and let program reload.");
            }

            List<File> pdfs = map.get(PDF_CSV_CAPTION).stream()
                    .map(e -> new File(e)).collect(Collectors.toList());
            List<Integer> pageNums = map.get(PAGENUM_CSV_CAPTION).stream()
                    .map(e -> Integer.parseInt(e)).collect(Collectors.toList());

            if (pdfs.size() != pageNums.size()) {
                throw new CSVException("csv file - column lengths vary, please " +
                        "delete csv file and let program reload.");
            }

            for (int i = 0; i < pdfs.size(); i++) {
                bookmarks.add(new Bookmark(pdfs.get(i), pageNums.get(i)));
            }
        }
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public PropertyValues getProps() {
        return props;
    }

    /**
     * Reads the config.properties file at the following directory and copies
     * it to the projects directory.
     * dir: ./com/dermacon/app/config.properties
     *
     * @return
     * @throws IOException
     */
    private File copyResourceConfig() throws IOException {
        File res_config = new File(PROPERTIES_DEFAULT_NAME);
        InputStream stream =
                getClass().getClassLoader().getResourceAsStream(PROPERTIES_RESOURCE_PATH);
        FileUtils.copyInputStreamToFile(stream, res_config);
        return res_config;
    }

    /**
     * Puts the given bookmark to the top of the history bookmarks.
     *
     * @param bookmark bookmark that should be put into the history file.
     * @throws IOException
     */
    public void prependsHistory(Bookmark bookmark) throws IOException {
//        System.out.println("appending history - " + bookmark.toString());

        String bm_selectedFile = bookmark.getFile().getPath();
        String history_content = HISTORY_HEADING + "\n"
                + bm_selectedFile
                + ',' + bookmark.getPageIdx();

        File history_file = props.getHistoryCSV();
        if (history_file == null || !history_file.exists() || history_file.isDirectory()) {
            history_file = new File(HISTORY_DEFAULT_PATH);
        } else {
            history_content += filterHistoryFile(bm_selectedFile);
        }

        FileUtils.writeStringToFile(history_file, history_content);
    }

    /**
     * Transforms the lines of the csv file to a string where each line if
     * seperated by a line break.
     * Only those lines which do not contain the selected file path and who
     * differ from the overall title / header line from the file will be
     * evaluated / appended.
     *
     * @param bm_selectedFile bookmark selected file path
     * @return String
     * @throws IOException
     */
    private String filterHistoryFile(String bm_selectedFile) throws IOException {
        // todo stream
//        input += Files.lines(Paths.get(bm_selectedFile))
//                .filter(e -> !e.contains(bm_selectedFile))
//                .collect(Collectors.joining("\n"));
//
        List<String> lines = FileUtils.readLines(props.getHistoryCSV(),
                StandardCharsets.UTF_8);
        StringBuilder filtered_content = new StringBuilder();
        for (String line : lines) {
            if (!line.contains(bm_selectedFile)
                    && !line.contains(HISTORY_HEADING)) {
                filtered_content.append(line);
            }
        }
        return filtered_content.toString();
    }

    /**
     * todo filter so that only pdfs will be selectable
     * Opens file chooser to select a pdf.
     * @return bookmark instance wrapped around the pdf
     */
    public Bookmark openNewBookmark() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        int opt = chooser.showOpenDialog(null);
        File out = null;
        if (opt == JFileChooser.APPROVE_OPTION) {
            out = chooser.getSelectedFile();
            System.out.println("User selected: " + out.getPath());
        }
        return new Bookmark(out, 0);
    }

    /**
     * Deletes temp directories
     * - config.properties
     * - rendered img directory
     */
    public void clean() throws IOException {
//        File history = props.getHistoryCSV();
//        System.out.println("Delete " + history);
//        FileUtils.forceDelete(history);

        File img = new File(props.getImgPath());
        System.out.println("delete " + img);
        FileUtils.forceDelete(img);

        File config = new File(PROPERTIES_DEFAULT_NAME);
        System.out.println("delete " + config);
        FileUtils.forceDelete(config);
    }


}
