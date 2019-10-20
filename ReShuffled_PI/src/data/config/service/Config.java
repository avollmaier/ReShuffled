package data.config.service;

import com.google.gson.*;
import data.config.data.ConfigModel;
import logging.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config {
    private static final Logger LOG = Logger.getLogger(Config.class.getName());
    private static ConfigModel instance = null;
    public static ConfigModel config;

    public static ConfigModel getInstance() {
        if (instance == null) {
            throw new RuntimeException("Instance not created yet");
        }
        return instance;
    }

    public static ConfigModel createInstance(String configPath) {
        if (instance != null) {
            throw new RuntimeException("Instance already created");
        } else {
            instance = readConfig(configPath);
        }
        return instance;
    }

    public static void updateInstace(String configPath) {
         if (instance == null) {
            throw new RuntimeException("Instance not created yet");
        }
        writeConfig(configPath, instance);
    }


    public static ConfigModel readConfig(String configPath) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(configPath), StandardCharsets.UTF_8))) {
            int length = 0;
            StringBuilder stringBuilder = new StringBuilder();           
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                length++;
            }
            LOG.info("readed %d lines successfully", length);
            Gson gson=new Gson();
            config = gson.fromJson(stringBuilder.toString(), ConfigModel.class);
            
        } catch (IOException ex) {
            LOG.severe("error while reading config file");
        }
        return config;
    }

    public static void writeConfig(String configPath, ConfigModel receivedConfig) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        config = receivedConfig;
        String json = gson.toJson(receivedConfig);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configPath), StandardCharsets.UTF_8))) {
            writer.write(json);
        } catch (IOException ex) {
            LOG.severe("error while writing config file");
        }
        LOG.info("serialized new config successfully");
    }
}
