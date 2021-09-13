package com.configs;

import org.json.simple.JSONObject;

//Stores default configs
// Acts as a core for .configs(handles exceptions, connects ConfigConnection and FileConnection etc)
public class ConfigMain {
    static private final JSONObject defaultConfigs = createDefaultConfigs();
    private JSONObject configsFromFile;

    JSONObject getConfigsFromFile(String filePath) {

        return null;
    }

    //Default configs are made here
    static private JSONObject createDefaultConfigs() {
        JSONObject defaultConfigs = new JSONObject();
        defaultConfigs.put("webServerIP", "127.0.0.1");
        defaultConfigs.put("webServerPort", 8080);
        return defaultConfigs;
    }
}
