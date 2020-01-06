package com.dermacon.app.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Source:
 * https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 */
public class CSVReader {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static Map<String, List<String>> readCSV(String name) throws IOException {
        List<List<String>> grid = readGrid(name);
        return createMap(grid);
    }

    private static List<List<String>> readGrid(String csvFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(csvFile));
        List<List<String>> grid = new LinkedList<>();
        while (scanner.hasNext()) {
            List<String> line = parseLine(scanner.nextLine());
            grid.add(line);
        }
        scanner.close();
        return grid;
    }

    private static Map<String, List<String>> createMap(List<List<String>> grid) {
        assert grid.size() > 0;
        Map<String, List<String>> out = new HashMap<>();

        // extracting headings from first line
        List<String> headings = grid.get(0);
        for (String heading : headings) {
            out.put(heading, new LinkedList<String>());
        }

        // extracting lines from rest -> the grid is mirrored allong the main
        // diagonal, that's why the loop looks so fishy.
        for (int column = 0; column < grid.get(0).size(); column++) {
            List<String> colContent = new LinkedList<>();
            for (int row = 1; row < grid.size(); row++) {
                colContent.add(grid.get(row).get(column));
            }
            out.put(headings.get(column), colContent);
        }

        return out;
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }


}
