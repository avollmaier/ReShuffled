
package config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

import config.ConfigLoader;
import logging.LogBackgroundHandler;
import logging.LogOutputStreamHandler;
import logging.Logger;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ConfigLoader
{
public static String configPath="/home/alois/Schreibtisch/Drive/RSmaster/ReShuffled/config/config.json";

public static void createConfigFile()
    {
        // pretty print
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        //create Java object
        Config config =new Config();


        // Java objects to String
        String json = gson.toJson(config);

        // Java objects to File
        try (FileWriter writer = new FileWriter(configPath)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readConfigFile()
    {


    }

}
