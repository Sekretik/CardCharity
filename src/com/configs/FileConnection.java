package com.configs;

import java.io.*;
import java.util.Scanner;

//Provides connection to text file - writing/reading
//If file doesn't exist, creates it or throws FileNotFoundException - depending on createFile variable
public class FileConnection {
    private DataOutputStream outputStream;
    private Scanner inputScanner;

    FileConnection(String filePath, boolean createFile) throws IOException {
        File file = new File(filePath);

        if(!file.exists()) {
            if(createFile) {
                file.createNewFile();
            } else {throw new FileNotFoundException("System cannot find " + filePath + " file");}
        }
        outputStream = new DataOutputStream(new FileOutputStream(file));
        inputScanner = new Scanner(new FileInputStream(file));
    }

    String readFile() {
        String fileContent = "";
        while(inputScanner.hasNextLine()) {
            fileContent = fileContent + inputScanner.nextLine();
        }
        return fileContent;
    }

    void writeToFile(String textData) {
        try {
            outputStream.writeUTF(textData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
