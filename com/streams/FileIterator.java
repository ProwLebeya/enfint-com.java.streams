package com.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class FileIterator {
    public static void main(String[] args) {
        try {
            File folder = new File(args[0]);//folder to iterates
            FileWriter output = new FileWriter(args[1]);//output file
            
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    FileIterator.getFolderFiles(file, output);
                } else {
                    FileIterator.copyToFile(file.getPath(), output);
                }
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
	 * Copy contents of current file to new file
	 * 
	 * @param currentDir
	 * @param writer
	 */
    private static void copyToFile(String currentDir, FileWriter writer) {
        try {
            FileReader reader;
            String currentLine;
            File currentFile = new File(currentDir); //creale file from path
            reader = new FileReader(currentFile);//reader to read file
            BufferedReader bufferedReader = new BufferedReader(reader);//buffer to read
            LineNumberReader lineReader = new LineNumberReader(bufferedReader);
        
            while ((currentLine = lineReader.readLine()) != null) {//value of current line
                if (currentLine.startsWith("*require") && currentLine.endsWith("*")) {
                    System.out.println("Extracting contents of child class");
                    copyToFile(currentLine.substring(10, currentLine.length() - 2), writer);
                 }
                 else {
                    writer.write(currentLine);
                    writer.write(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
	 * Get files from current folder
	 * 
	 * @param file
	 * @param wiriter
	 */
    private static void getFolderFiles(File file, FileWriter writer) {
        for (File subFolder : file.listFiles()) {
            if (subFolder.isDirectory()) {
                getFolderFiles(subFolder, writer);
            } else {
                copyToFile(subFolder.getPath(), writer);
            }
        }
    }
}


