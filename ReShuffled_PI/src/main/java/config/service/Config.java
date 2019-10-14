package config.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logging.Logger;
import config.data.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigService
{
    private static final Logger LOG = Logger.getLogger(ConfigService.class.getName());
    private Config config;

    public Config deserializeService(String configPath)
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(configPath), StandardCharsets.UTF_8)))
        {
            int length=0;
            StringBuilder stringBuilder = new StringBuilder();
            String line=null;

            LOG.info("reading File at %s", configPath);
            while ((line =bufferedReader.readLine()) != null){
                stringBuilder.append(line);
                length++;
            }
            LOG.info("readed %d lines successfully", length);
            Gson gson =new Gson();
            config = gson.fromJson(stringBuilder.toString(), Config.class);
        }
        catch(IOException ex)
        {
            LOG.severe("error while reading config file");
        }
        return config;
    }

    public void serializeService(String configPath, Config receivedConfig)
    {
        Gson gson =new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        config=receivedConfig;
        String json = gson.toJson(receivedConfig);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configPath), StandardCharsets.UTF_8)))
        {
            writer.write(json);
        }
        catch(IOException ex)
        {
            LOG.severe("error while writing config file");
        }
        LOG.info("serialized new config successfully");
    }

}
