package com.configs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

//Provides connection to text file - writing/reading
//If file doesn't exist, creates it or throws FileNotFoundException - depending on createFile variable
public class FileConnection {
    private boolean newFileCreated; // Is set true if new file was created
    Path configFilePath;

    FileConnection(String filePath, boolean createFile) throws FileNotFoundException {
        newFileCreated = false;
        configFilePath = Path.of(filePath);
        if(!Files.exists(configFilePath) && createFile) {
            try {
                Files.createFile(configFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            newFileCreated = true;
        }
        else if(!createFile) {
            throw new FileNotFoundException("File " + configFilePath.toString() + " not found");
        }
    }

    String readFile() {
        String fileContent = "";
        try {
            fileContent = Files.readString(configFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    void writeToFile(String textData) {
        try {
            Files.writeString(configFilePath, textData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isNewFileCreated() {
        return newFileCreated;
    }
}
