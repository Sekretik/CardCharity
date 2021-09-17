package com.configs;

//Provides interface for interacting with configs module
public class ConfigConnection {
    ConfigMain configMain;

    public ConfigConnection() {
        configMain = new ConfigMain();
    }

    public void setConfigFile(String filePath) {
        configMain.getConfigsFromFile(filePath);
    }

    public String getConfigValue(String key) throws Exception {
        return configMain.getConfigValue(key);
    }
}