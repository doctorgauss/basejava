package com.urise.webapp;

import java.io.File;
import java.util.List;

public class MainFile {
    public static void main(String[] args) {
        printNameOfFiles(new File("./src/com/urise/webapp"));

    }

    public static void printNameOfFiles(File file){
        if (file == null || !file.exists()) return;

        File[] files = file.listFiles();
        if (files == null) return;

        for (File f : files){
            if (f.isDirectory()){
                printNameOfFiles(f);
            } else {
                System.out.println(f.getName());
            }
        }
    }
}
