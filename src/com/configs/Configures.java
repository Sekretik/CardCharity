package com.conf;

import org.json.simple.*;
import java.io.*;
import java.util.Scanner;

//This class reads configs from the directory with program files

public class Configures {
    static Configures configures = null;
    private final String configureFilePath = "./config";
    private final JSONObject defaultConfigures = getDefaultConfig();
    private JSONObject jsonConfigures;

    private Configures() {
        File configFile = new File(configureFilePath);

        //If there is no config file, it will be created using default values
        //Current configs will also be set to default
        if(!configFile.exists()) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(configFile);
                configFile.createNewFile();
                fileWriter.write(defaultConfigures.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            jsonConfigures = defaultConfigures;
        }
        else {
            FileReader fileReader = null;
            Scanner scanner = null;

            try {
                fileReader = new FileReader(configFile);
                scanner = new Scanner(fileReader);
                jsonConfigures = (JSONObject) JSONValue.parse(scanner.nextLine());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scanner.close();
            }

        }
    }

    public String getConfigValue(String key) {
        String value = jsonConfigures.get(key).toString();
        if(value == null) {
            value = defaultConfigures.get(key).toString();
        }
        return value;
    }

    public static Configures getConfigures() {
        if(configures == null) {configures = new Configures();}
        return configures;
    }

    //Default configs
    private JSONObject getDefaultConfig() {
        JSONObject defaultConfigs = new JSONObject();
        defaultConfigs.put("serverPort", 80);
        defaultConfigs.put("serverIP", "127.0.0.1");
        return defaultConfigs;
    }

    public static void main(String[] args) {
        getConfigures();
        System.out.println(configures.getConfigValue("serverPort"));
    }
}
