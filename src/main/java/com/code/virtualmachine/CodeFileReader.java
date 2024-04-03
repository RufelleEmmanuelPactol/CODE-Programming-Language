package com.code.virtualmachine;

import com.code.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CodeFileReader {
    private BufferedReader reader;



    public CodeFileReader(String filePath) throws FileNotFoundException {
        System.err.println("Initializing CodeFileReader");
        File file = new File(Main.args.length > 0 ? Main.args[0] : "main.code");
        // Get the parent directory of the current file
        System.out.println(file.getParent() + filePath);
        String directoryPath = file.getParent();

        reader = new BufferedReader(new java.io.FileReader(directoryPath + filePath));
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (Exception e) {
            // do nothing
        }
    }

    public String readAndAppend() {
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (Exception e) {
            return null;
        }
        return builder.toString();
    }

    public ArrayList<String> readAllLines() {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            return null;
        }
        return lines;
    }



}
