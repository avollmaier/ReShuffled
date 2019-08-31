package config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import config.Models.Config;
import logging.Logger;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigService
{
    private static final Logger LOG = Logger.getLogger(ConfigService.class.getName());
    public static final String configPath="/home/alois/Schreibtisch/Drive/ReShuffled/ReShuffled/config/config.json";
    private static Config config;

    public static void deserializeService()
    {
        try (BufferedReader bufferedReader  = new BufferedReader(new FileReader(configPath)))
        {
            int length=0;
            StringBuffer stringBuffer =new StringBuffer();
            String line=null;

            LOG.info("reading File at %s", configPath);
            while ((line =bufferedReader.readLine()) != null){
                stringBuffer.append(line);
                length++;
            }
            LOG.info("readed %d lines successfully", length);
            Gson gson =new Gson();
            config = gson.fromJson(stringBuffer.toString(),Config.class);
        }
        catch(IOException ex)
        {
            LOG.severe("error while reading config file");
        }
    }

    public static void serializeService(Config receivedConfig)
    {
        //build JSON from Java Object
        Gson gson =new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        config=receivedConfig;
        String json = gson.toJson(receivedConfig);

        try (FileWriter writer = new FileWriter(configPath);)
        {
            writer.write(json);
        }
        catch(IOException ex)
        {
            LOG.severe("error while writing config file");
        }
    }
}
