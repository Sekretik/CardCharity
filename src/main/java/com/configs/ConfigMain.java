package com.configs;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import org.slf4j.*;

//Stores default configs
// Acts as a core for .configs(handles exceptions, connects ConfigConnection and FileConnection etc)
public class ConfigMain {
    static private final Logger logger = LoggerFactory.getLogger(ConfigMain.class);
    static private final JSONObject defaultConfigs = createDefaultConfigs();
    private JSONObject configsFromFile;

    //gets configs via FileConnection
    void getConfigsFromFile(String filePath) {
        FileConnection fileConnection;
        try {
            fileConnection = new FileConnection(filePath, true);
            if(fileConnection.isNewFileCreated()) {
                fileConnection.writeToFile(defaultConfigs.toJSONString());
            }
            String jsonConfigString = fileConnection.readFile();

            if(jsonConfigString.isEmpty()) {
                fileConnection.writeToFile(defaultConfigs.toJSONString());
            }
            configsFromFile = (JSONObject) JSONValue.parse(fileConnection.readFile());
        } catch (IOException e) {

        }
    }

    //Gets config value by key
    // If there's no such config in configsFromFile returns default value
    String getConfigValue(String key) throws Exception {
        String value = null;
        try {value = configsFromFile.get(key).toString();}
        catch (NullPointerException e) {
            //write to logs
        }
        if(value == null) {
            value = defaultConfigs.get(key).toString();
            if(value == null) {
                //Logs exception
                throw new Exception("ConfigNotFound");}
        }
        return value;
    }

    //Default configs are made here
    static private JSONObject createDefaultConfigs() {
        JSONObject defaultConfigs = new JSONObject();
        defaultConfigs.put("webServerIP", "127.0.0.1");
        defaultConfigs.put("webServerPort", 8080);
        return defaultConfigs;
    }
}
